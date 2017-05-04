package hackeru.haim.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_SMS = 2;
    EditText etMessage;
    EditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText) findViewById(R.id.etMessage);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

    }

    public void sendSms(View view) {
//        Uri uri = Uri.parse("smsto:" + etPhoneNumber.getText());
//        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//
//        intent.putExtra("sms_body", etMessage.getText().toString());
//
//            startActivity(intent);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS);

            return;
        }
        SmsManager.getDefault().sendTextMessage(etPhoneNumber.getText().toString(),
                null,
                etMessage.getText().toString(),
                null,
                null
        );
    }

    public void call(@Nullable View view) {
        Uri uri = Uri.parse("tel:" + etPhoneNumber.getText());
        Intent callIntent = new Intent(Intent.ACTION_CALL, uri);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                call(null);
            else
                Toast.makeText(this, "No Call for you... ;)", Toast.LENGTH_SHORT).show();
        }

        switch (requestCode){
            case REQUEST_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    call(null);
                else
                    Toast.makeText(this, "No Call for you... ;)", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    sendSms(null);
                else
                    Toast.makeText(this, "No message for you... ;)", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
