package com.example.mojfax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat1 extends AppCompatActivity {


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        ImageView messageImageView;
        TextView messengerTextView;
        ImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }




    private static final String TAG = "MainActivity2";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://thumbs.gfycat.com/AjarDisguisedAfricanparadiseflycatcher-max-1mb.gif";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mUIDsender;
    private String mUIDReceiver;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;

    private static final String MESSAGE_URL = "http://friendzone.firebase.google.com/message/";

    private ImageButton mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private ImageView mAddMessageImageView;
    String fakultet;
    String odsjek;
    String godina;
    String uni;
    // Firebase instance variables
    private DatabaseReference mDatabase;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


        if (!(bundle.getString("stuf")==null)) {
            uni = bundle.getString("stuf");
        } else {
            uni = preferences.getString("uni","");
        }


        if (!(bundle.getString("stuff")==null)) {
            fakultet = bundle.getString("stuff");
        } else {
            fakultet = preferences.getString("jedan","");
        }


        if (!(bundle.getString("stuff1")==null)) {
            odsjek = "_" + bundle.getString("stuff1");
        } else {
            odsjek = " ";
        }

        if (!(bundle.getString("stuff2")==null)) {
            godina = "_" + bundle.getString("stuff2");
        } else {
            godina = " ";
        }
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        mUIDsender = ANONYMOUS;
        mUIDReceiver = ANONYMOUS;
        //Initialize firebase auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser == null) {
            //not signed in launch the sign in activity
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseAuth.getCurrentUser().getDisplayName();
            mUIDsender = mFirebaseAuth.getCurrentUser().getUid();

            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor1 = preferences.edit();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(uni.equals("_") && fakultet.equals("_")) {
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());

                    if (broj1 != 0) {
                        String s = "broj" + broj1;
                        Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                        editor1.putLong("broj1c", broj1);

                        editor1.apply();
                    }
                }
                    else if(fakultet.equals("_")){
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());
                    String s = "broj" + broj1;
                    Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                    editor1.putLong("broj2c", broj1);

                    editor1.apply();
                }
                else if(odsjek.equals(" ") && godina.equals(" ")){
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());
                    String s = "broj" + broj1;
                    Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                    editor1.putLong("broj3c", broj1);

                    editor1.apply();
                }
                else if(godina.equals(" ")){
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());
                    String s = "broj" + broj1;
                    Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                    editor1.putLong("broj4c", broj1);

                    editor1.apply();
                }
                else if (uni.equals("_") && fakultet != ("_")){
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());
                    String s = "broj" + broj1;
                    Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                    editor1.putLong("broj6c", broj1);

                    editor1.apply();
                }else{
                    long broj1 = (dataSnapshot.child("messages").child(uni).child(fakultet).child(fakultet + odsjek + godina).getChildrenCount());
                    String s = "broj" + broj1;
                    Toast.makeText(Chat1.this, s, Toast.LENGTH_SHORT).show();
                    editor1.putLong("broj5c", broj1);

                    editor1.apply();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mDatabase.addValueEventListener(postListener);


        if (fakultet != " ") {
            // Initialize ProgressBar and RecyclerView.
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setStackFromEnd(true);
            mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

            // New child entries
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
            SnapshotParser<FriendlyMessage> parser = new SnapshotParser<FriendlyMessage>() {
                @Override
                public FriendlyMessage parseSnapshot(DataSnapshot dataSnapshot) {
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    if (friendlyMessage != null) {
                        friendlyMessage.setId(dataSnapshot.getKey());
                    }

                    return friendlyMessage;
                }
            };


            DatabaseReference messagesRef = mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(uni).child(fakultet).child(fakultet + odsjek + godina);
            FirebaseRecyclerOptions<FriendlyMessage> options =
                    new FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                            .setQuery(messagesRef, parser)
                            .build();

            mFirebaseAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(options) {
                @Override
                public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    //Toast.makeText(MainActivity2.this, mUIDReceiver, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity2.this, "primio", Toast.LENGTH_SHORT).show();
                        return new MessageViewHolder(inflater.inflate(R.layout.their_message, viewGroup, false));

                    // TODO: OVO NE RADI NEMOJ SE BUNIT
                    // TODO: ADD CODE TO SEE IF MESSAGE IS SENT BY USER OR RECEIVED
                }




                @Override
                protected void onBindViewHolder(final MessageViewHolder viewHolder,
                                                int position,
                                                FriendlyMessage friendlyMessage) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    // mUIDReceiver = null;
                    if (friendlyMessage.getText() != null) {
                        String s = friendlyMessage.getText();
                        if(s.substring(s.length()-1,1) != " "){
                            s = s+" ";
                        }
                        int j=0;
                        for(int i =0; i<s.length();i++){
                            String g= s.substring(j,i);
                            String h = s.substring(i,1);
                            if(h==" "){
                                j=i;
                            }
                            URLUtil.isValidUrl(g);

                        }
                        viewHolder.messageTextView.setText(friendlyMessage.getText());


                        viewHolder.messageTextView.setVisibility(TextView.VISIBLE);
                        viewHolder.messageImageView.setVisibility(ImageView.GONE);
                        mUIDReceiver = friendlyMessage.getUID();


                    } else if (friendlyMessage.getImageUrl() != null) {
                        String imageUrl = friendlyMessage.getImageUrl();
                        mUIDReceiver = friendlyMessage.getUID();

                        if (imageUrl.startsWith("gs://")) {
                            StorageReference storageReference = FirebaseStorage.getInstance()
                                    .getReferenceFromUrl(imageUrl);
                            mUIDReceiver = friendlyMessage.getUID();

                            storageReference.getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                String downloadUrl = task.getResult().toString();
                                                Glide.with(viewHolder.messageImageView.getContext())
                                                        .load(downloadUrl)
                                                        .into(viewHolder.messageImageView);


                                            } else {
                                                Log.w(TAG, "Getting download url was not successful.",
                                                        task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Glide.with(viewHolder.messageImageView.getContext())
                                    .load(friendlyMessage.getImageUrl())
                                    .into(viewHolder.messageImageView);
                            mUIDReceiver = friendlyMessage.getUID();

                        }
                        viewHolder.messageImageView.setVisibility(ImageView.VISIBLE);
                        viewHolder.messageTextView.setVisibility(TextView.GONE);
                        mUIDReceiver = friendlyMessage.getUID();

                    }

                    // friendlyMessage.setName(mFirebaseAuth.getCurrentUser().getDisplayName());
                    mUIDReceiver = friendlyMessage.getUID();
                    viewHolder.messengerTextView.setText(friendlyMessage.getName());


                    if (friendlyMessage.getPhotoUrl() == null) {
                        viewHolder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(Chat1.this,
                                R.drawable.ic_account_circle_black_36dp));

                    } else {
                        Glide.with(Chat1.this)
                                .load(friendlyMessage.getPhotoUrl())
                                .into(viewHolder.messengerImageView);

                    }

                }

            };

            mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                    int lastVisiblePosition =
                            mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    // If the recycler view is initially being loaded or the
                    // user is at the bottom of the list, scroll to the bottom
                    // of the list to show the newly added message.
                    if (lastVisiblePosition == -1 ||
                            (positionStart >= (friendlyMessageCount - 1) &&
                                    lastVisiblePosition == (positionStart - 1))) {
                        mMessageRecyclerView.scrollToPosition(positionStart);
                    }
                }
            });

            // TODO mogo bi ovdje bit bug

            mMessageRecyclerView.setAdapter(mFirebaseAdapter);

            mMessageEditText = (EditText) findViewById(R.id.messageEditText);
            mMessageEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        mSendButton.setEnabled(true);
                    } else {
                        mSendButton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            mSendButton = findViewById(R.id.sendButton);
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FriendlyMessage friendlyMessage = new
                            FriendlyMessage(mMessageEditText.getText().toString(), mUsername, mUIDsender, mPhotoUrl, null);
                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(uni).child(fakultet).child(fakultet + odsjek + godina).push().setValue(friendlyMessage);
                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(uni).child("zadnja").child(fakultet).child(fakultet + odsjek + godina).setValue(friendlyMessage);

                    mMessageEditText.setText("");
                }
            });

            mAddMessageImageView = (ImageView) findViewById(R.id.addMessageImageView);
            mAddMessageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
            });
        }else{
            Toast.makeText(Chat1.this, "Povezite se na mrezu", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {

            mFirebaseAdapter.stopListening();
            super.onPause();

    }

    @Override
    public void onResume() {

            super.onResume();
            mFirebaseAdapter.startListening();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ",resultCode=" + resultCode);

            if (requestCode == REQUEST_IMAGE) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        final Uri uri = data.getData();
                        Log.d(TAG, "Uri: " + uri.toString());

                        FriendlyMessage tempMessage = new FriendlyMessage(null, mUsername, mUIDsender,
                                mPhotoUrl, LOADING_IMAGE_URL);
                        mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(uni).child(fakultet).child(fakultet + odsjek + godina).push()
                                .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError,
                                                           DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            String key = databaseReference.getKey();
                                            StorageReference storageReference = FirebaseStorage.getInstance()
                                                    .getReference(mFirebaseUser.getUid())
                                                    .child(key)
                                                    .child(uri.getLastPathSegment());

                                            putImageInStorage(storageReference, uri, key);
                                        } else {
                                            Log.w(TAG, "Unable to write message to database.",
                                                    databaseError.toException());
                                        }
                                    }
                                });
                    }
                }
            }
        }


    private void putImageInStorage(StorageReference storageReference,Uri uri,final String key){
        storageReference.putFile(uri).addOnCompleteListener(Chat1.this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            task.getResult().getMetadata().getReference().getDownloadUrl()
                                    .addOnCompleteListener(Chat1.this, new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if(task.isSuccessful()){
                                                FriendlyMessage friendlyMessage = new FriendlyMessage(
                                                        null,mUsername,mUIDsender,mPhotoUrl,task.getResult().toString());
                                                mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(uni).child(fakultet).child(fakultet+odsjek+godina).child(key)
                                                        .setValue(friendlyMessage);
                                               // Toast.makeText(Chat1.this,task.getResult().toString(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Log.w(TAG,"Image upload task was not successful.",task.getException());
                        }
                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Chat1.this, TestActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();


                mUsername = ANONYMOUS;
                //mUIDReceiver = ANONYMOUS;
                mUIDsender= ANONYMOUS;
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
