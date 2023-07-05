package com.example.juda.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juda.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatingActivity extends AppCompatActivity {

    private String chatRoomUid; //채팅방 하나 id
    private String myuid;       //나의 id
    private String destUid;     //상대방 uid

    private RecyclerView recyclerView;
    private Button button;
    private EditText editText;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference dbReference;
    private UserInfo destUser;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);


        init();
        sendMsg();
    }
    private void init()
    {
        myuid = "user1";//FirebaseAuth.getInstance().getCurrentUser().getUid();
        destUid = "user2";        //채팅 상대

        recyclerView = (RecyclerView)findViewById(R.id.msg_recyclerView);
        button=(Button)findViewById(R.id.btn_msg);
        editText = (EditText)findViewById(R.id.et_msg);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("test");
        if(editText.getText().toString() == null) button.setEnabled(false);
        else button.setEnabled(true);

        checkChatRoom();
    }
    private void sendMsg()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.users.put(myuid,true);
                chatInfo.users.put(destUid,true);

                //push() 데이터가 쌓이기 위해 채팅방 key가 생성
                if(chatRoomUid == null){
                    button.setEnabled(false);
                    firebaseDatabase.getReference().child("chatrooms").push().setValue(chatInfo).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("chatAct", "성공!");
                            checkChatRoom();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("chatAct", e.toString());
                        }
                    });
                    Log.d("chatAct", "밖!");
                }else{
                    sendMsgToDataBase();
                }
            }
        });
    }
    private void sendMsgToDataBase()
    {
        if(!editText.getText().toString().equals(""))
        {
            ChatInfo.Comment comment = new ChatInfo.Comment();
            comment.uid = myuid;
            comment.message = editText.getText().toString();
            comment.timestamp = ServerValue.TIMESTAMP;
            firebaseDatabase.getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    editText.setText("");
                }
            });
        }
    }
    private void checkChatRoom()
    {
        //자신 key == true 일때 chatModel 가져온다.
        /* chatModel
        public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저
        public Map<String, ChatModel.Comment> comments = new HashMap<>(); //채팅 메시지
        */
        firebaseDatabase.getReference().child("chatrooms").orderByChild("users/"+myuid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) //나, 상대방 id 가져온다.
                {
                    Toast.makeText(ChatingActivity.this, "checkChatRoom", Toast.LENGTH_SHORT).show();
                    ChatInfo chatInfo = dataSnapshot.getValue(ChatInfo.class);
                    if(chatInfo.users.containsKey(destUid)){           //상대방 id 포함돼 있을때 채팅방 key 가져옴
                        chatRoomUid = dataSnapshot.getKey();
                        button.setEnabled(true);

                        //동기화
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChatingActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());

                        //메시지 보내기
                        sendMsgToDataBase();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
    {
        List<ChatInfo.Comment> comments;

        public RecyclerViewAdapter(){
            comments = new ArrayList<>();

            getDestUid();
        }

        //상대방 uid 하나(single) 읽기
        private void getDestUid()
        {
            firebaseDatabase.getReference().child("users").child(destUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    destUser = snapshot.getValue(UserInfo.class);

                    //채팅 내용 읽어들임
                    getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        //채팅 내용 읽어들임
        private void getMessageList()
        {
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        comments.add(dataSnapshot.getValue(ChatInfo.Comment.class));
                    }
                    notifyDataSetChanged();

                    recyclerView.scrollToPosition(comments.size()-1);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_messagebox,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            ViewHolder viewHolder = ((ViewHolder)holder);

            if(comments.get(position).uid.equals(myuid)) //나의 uid 이면
            {
                //나의 말풍선 오른쪽으로
                viewHolder.textViewMsg.setText(comments.get(position).message);
                viewHolder.textViewMsg.setBackgroundResource(R.drawable.round_background);
                viewHolder.linearLayoutDest.setVisibility(View.INVISIBLE);        //상대방 레이아웃
                viewHolder.linearLayoutRoot.setGravity(Gravity.RIGHT);
                viewHolder.linearLayoutTime.setGravity(Gravity.RIGHT);
            }else{
                //상대방 말풍선 왼쪽
                /*Glide.with(holder.itemView.getContext())
                        .load(destUser.profileImgUrl)
                        .apply(new RequestOptions().circleCrop())
                        .into(holder.imageViewProfile);*/
                viewHolder.textViewName.setText(destUser.name);
                viewHolder.linearLayoutDest.setVisibility(View.VISIBLE);
                viewHolder.textViewMsg.setBackgroundResource(R.drawable.round_background);
                viewHolder.textViewMsg.setText(comments.get(position).message);
                viewHolder.linearLayoutRoot.setGravity(Gravity.LEFT);
                viewHolder.linearLayoutTime.setGravity(Gravity.LEFT);
            }
            viewHolder.textViewTimeStamp.setText(getDateTime(position));

        }

        public String getDateTime(int position)
        {
            long unixTime=(long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            return time;
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView textViewMsg;   //메시지 내용
            public TextView textViewName;
            public TextView textViewTimeStamp;
            public ImageView imageViewProfile;
            public LinearLayout linearLayoutDest;
            public LinearLayout linearLayoutRoot;
            public LinearLayout linearLayoutTime;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewMsg = (TextView)itemView.findViewById(R.id.item_messagebox_textview_msg);
                textViewName = (TextView)itemView.findViewById(R.id.item_messagebox_TextView_name);
                textViewTimeStamp = (TextView)itemView.findViewById(R.id.item_messagebox_textview_timestamp);
                imageViewProfile = (ImageView)itemView.findViewById(R.id.item_messagebox_ImageView_profile);
                linearLayoutDest = (LinearLayout)itemView.findViewById(R.id.item_messagebox_LinearLayout);
                linearLayoutRoot = (LinearLayout)itemView.findViewById(R.id.item_messagebox_root);
                linearLayoutTime = (LinearLayout)itemView.findViewById(R.id.item_messagebox_layout_timestamp);
            }
        }
    }
}
