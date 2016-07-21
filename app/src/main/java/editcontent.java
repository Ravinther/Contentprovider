import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;

import com.example.ravi.contentprovider.R;

/**
 * Created by Ravi on 7/21/2016.
 */
public class editcontent extends Activity {
    private EditText mEmailAddress = (EditText) findViewById(R.id.et_home_email);
    private EditText mPhoneNumber = (EditText) findViewById(R.id.et_home_phone);
    public Cursor mCursor;
    // The index of the lookup key column in the cursor
    public int mLookupKeyIndex;
    public int mIdIndex;
    public String mCurrentLookupKey;
    public long mCurrentId;
    Uri mSelectedContactUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intentInsertEdit = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        // Sets the MIME type
        intentInsertEdit.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        startActivity(intentInsertEdit);

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mEmailAddress.getText())
        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.PHONE, mPhoneNumber.getText())
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        startActivity(intent);

        mLookupKeyIndex = mCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
        // Gets the lookup key value
        mCurrentLookupKey = mCursor.getString(mLookupKeyIndex);
        // Gets the _ID column index
        mIdIndex = mCursor.getColumnIndex(ContactsContract.Contacts._ID);
        mCurrentId = mCursor.getLong(mIdIndex);
        mSelectedContactUri =
                ContactsContract.Contacts.getLookupUri(mCurrentId, mCurrentLookupKey);
        Intent editIntent = new Intent(Intent.ACTION_EDIT);

        editIntent.setDataAndType(mSelectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        editIntent.putExtra("finishActivityOnSaveCompleted", true);
        startActivity(editIntent);



    }
}
