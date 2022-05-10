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
import com.google.firebase.FirebaseError;
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

public class ObavijestiActivity extends AppCompatActivity {


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        ImageView messageImageView;
        TextView messengerTextView;
        CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView1);
            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView1);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView1);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView1);
        }
    }




    private static final String TAG = "MainActivity2";
    public static final String MESSAGES_CHILD = "obavijesti";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
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
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FriendlyClanak, MessageViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obavijesti);
        Bundle bundle = getIntent().getExtras();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


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


            // Initialize ProgressBar and RecyclerView.
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
            mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView1);
            mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setStackFromEnd(true);
            mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

            // New child entries
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
            SnapshotParser<FriendlyClanak> parser = new SnapshotParser<FriendlyClanak>() {
                @Override
                public FriendlyClanak parseSnapshot(DataSnapshot dataSnapshot) {
                    FriendlyClanak friendlyClanak = dataSnapshot.getValue(FriendlyClanak.class);
                    if (friendlyClanak != null) {
                        friendlyClanak.setId(dataSnapshot.getKey());
                    }

                    return friendlyClanak;
                }
            };


            DatabaseReference messagesRef = mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(fakultet).child(fakultet + odsjek + godina);
            FirebaseRecyclerOptions<FriendlyClanak> options =
                    new FirebaseRecyclerOptions.Builder<FriendlyClanak>()
                            .setQuery(messagesRef, parser)
                            .build();

            mFirebaseAdapter = new FirebaseRecyclerAdapter<FriendlyClanak, MessageViewHolder>(options) {
                @Override
                public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    //Toast.makeText(MainActivity2.this, mUIDReceiver, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity2.this, "poslo", Toast.LENGTH_SHORT).show();

                        return new MessageViewHolder(inflater.inflate(R.layout.item_obavijesti, viewGroup, false));


                    // TODO: OVO NE RADI NEMOJ SE BUNIT
                    // TODO: ADD CODE TO SEE IF MESSAGE IS SENT BY USER OR RECEIVED
                }


                @Override
                protected void onBindViewHolder(final MessageViewHolder viewHolder,
                                                int position,
                                                FriendlyClanak friendlyClanak) {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    // mUIDReceiver = null;
                    if (friendlyClanak.getText() != null) {
                        viewHolder.messageTextView.setText(friendlyClanak.getText());


                        viewHolder.messageTextView.setVisibility(TextView.VISIBLE);
                        viewHolder.messageImageView.setVisibility(ImageView.GONE);
                        //mUIDReceiver = friendlyClanak.getUID();


                    } else if (friendlyClanak.getImageUrl() != null) {
                        String imageUrl = friendlyClanak.getImageUrl();
                       // mUIDReceiver = friendlyClanak.getUID();

                        if (imageUrl.startsWith("gs://")) {
                            StorageReference storageReference = FirebaseStorage.getInstance()
                                    .getReferenceFromUrl(imageUrl);
                           // mUIDReceiver = friendlyClanak.getUID();

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
                                    .load(friendlyClanak.getImageUrl())
                                    .into(viewHolder.messageImageView);
                            //mUIDReceiver = friendlyClanak.getUID();

                        }
                        viewHolder.messageImageView.setVisibility(ImageView.VISIBLE);
                        viewHolder.messageTextView.setVisibility(TextView.GONE);
                        //mUIDReceiver = friendlyClanak.getUID();

                    }

                    // friendlyMessage.setName(mFirebaseAuth.getCurrentUser().getDisplayName());
                   // mUIDReceiver = friendlyMessage.getUID();
                    viewHolder.messengerTextView.setText(friendlyClanak.getName());


                    if (friendlyClanak.getPhotoUrl() == null) {
                        viewHolder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(ObavijestiActivity.this,
                                R.drawable.ic_account_circle_black_36dp));

                    } else {
                        Glide.with(ObavijestiActivity.this)
                                .load(friendlyClanak.getPhotoUrl())
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

                    FriendlyClanak tempMessage = new FriendlyClanak(null, mUsername,
                            mPhotoUrl, LOADING_IMAGE_URL);
                    mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(fakultet).child(fakultet + odsjek + godina).push()
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
        storageReference.putFile(uri).addOnCompleteListener(ObavijestiActivity.this,
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            task.getResult().getMetadata().getReference().getDownloadUrl()
                                    .addOnCompleteListener(ObavijestiActivity.this, new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if(task.isSuccessful()){
                                                FriendlyClanak friendlyClanak = new FriendlyClanak(
                                                        null,mUsername,mPhotoUrl,task.getResult().toString());
                                                mFirebaseDatabaseReference.child(MESSAGES_CHILD).child(fakultet).child(fakultet+odsjek+godina).child(key)
                                                        .setValue(friendlyClanak);
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
