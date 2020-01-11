package ojass20.nitjsr.in.ojass.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import ojass20.nitjsr.in.ojass.R;

public class RegistrationPage extends AppCompatActivity {

    private TextInputEditText name_reg, email_reg,mobile_reg,
        college_reg, branch_reg;
    private Spinner tshirt_size_spinner;
    private Button register_button;
    private CircleImageView self_image;
    private TextView over_text;

    private String[] tshirt_sizes_list={"S","M","L","XL","XXL"};

    private FirebaseAuth mauth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ojass20.nitjsr.in.ojass.R.layout.activity_registration_page);

        init();

        ArrayAdapter<String> madap = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,tshirt_sizes_list);
        tshirt_size_spinner.setAdapter(madap);

        mauth = FirebaseAuth.getInstance();
        current_user_id = mauth.getCurrentUser().getUid();

        over_text.setText("Welcome "+mauth.getCurrentUser().getDisplayName());

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_user();
            }
        });

        Glide.with(this)
                .load(mauth.getCurrentUser().getPhotoUrl().toString())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        self_image.setImageResource(R.drawable.ic_mood_black_24dp);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(self_image);
    }

    private void register_user() {
        DatabaseReference dref = FirebaseDatabase
                .getInstance().getReference("Users");

        HashMap<String,Object> data = new HashMap<>();

        data.put("username",name_reg.getText().toString());
        data.put("email",email_reg.getText().toString());
        data.put("mobile",mobile_reg.getText().toString());
        data.put("college",college_reg.getText().toString());
        data.put("branch",branch_reg.getText().toString());

        data.put("tshirtSize",tshirt_sizes_list[tshirt_size_spinner.getSelectedItemPosition()]);

        String pic_url = "";
        pic_url = mauth.getCurrentUser().getPhotoUrl().toString();
        data.put("photoUrl",pic_url);
        data.put("uid",current_user_id);

        //String temp_key = dref.push().getKey();
        dref.child(current_user_id).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationPage.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(RegistrationPage.this, "Error: Not registered", Toast.LENGTH_SHORT).show();
                            Log.e("onComplete: "," "+task.getException().toString());
                            startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void init() {
        name_reg = findViewById(R.id.Name_Registration_page);
        email_reg = findViewById(R.id.Email_Registration_page);
        mobile_reg = findViewById(R.id.Mobile_Registration_page);
        college_reg = findViewById(R.id.College_Registration_page);
        branch_reg = findViewById(R.id.Branch_Registration_page);
        tshirt_size_spinner = findViewById(R.id.TShirt_Size_Registration_page);
        register_button = findViewById(R.id.register_button_Registration_page);
        self_image = findViewById(R.id.register_self_pic);
        over_text = findViewById(R.id.overlap_text);
    }

}
