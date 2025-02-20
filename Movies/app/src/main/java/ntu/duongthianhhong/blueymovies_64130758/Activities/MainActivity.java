package ntu.duongthianhhong.blueymovies_64130758.Activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ntu.duongthianhhong.blueymovies_64130758.R;
import ntu.duongthianhhong.blueymovies_64130758.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new ExplorerFragment());
        binding.bottomNavigationMenu.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.favorites) {
                replaceFragment(new FavoriteFragment());
            }
            if(item.getItemId() == R.id.explorer) {
                replaceFragment(new ExplorerFragment());
            }
            if(item.getItemId() == R.id.cart) {
                replaceFragment(new CartFragment());
            }
            if(item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        // giúp layout của cửa sổ vượt ra ngoài giới hạn thông thường của màn hình.
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}