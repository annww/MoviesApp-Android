package ntu.duongthianhhong.blueymovies_64130758.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import eightbitlab.com.blurview.RenderScriptBlur;
import ntu.duongthianhhong.blueymovies_64130758.Adapters.CastListAdapter;
import ntu.duongthianhhong.blueymovies_64130758.Adapters.CategoryEachFilmAdapter;
import ntu.duongthianhhong.blueymovies_64130758.Domains.Film;
import ntu.duongthianhhong.blueymovies_64130758.R;
import ntu.duongthianhhong.blueymovies_64130758.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView5 = findViewById(R.id.imageView5);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent chia sẻ
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                // Nội dung chia sẻ
                String title = "Xem phim tuyệt vời này!";
                String link = "https://play.google.com/store/apps/details?id=vn.duongthianhhong.sharelink";

                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_TEXT, link);

                // Bắt đầu Intent chia sẻ
                startActivity(Intent.createChooser(intent, "Chia sẻ qua"));
            }
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    private void setVariable() {
        Film item = (Film) getIntent().getSerializableExtra("object");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new GranularRoundedCorners(0, 0, 50, 50));

        Glide.with(this)
                .load(item.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);
        binding.titleTxt.setText(item.getTitle());
        binding.imdbTxt.setText("IMDB " + item.getImdb());
        binding.moviesTimesTxt.setText((item.getYear() + " - " + item.getTime()));
        binding.movieSummery.setText(item.getDescription());
        binding.watchTrailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getTrailer().replace("https://www.youtube.com/watch?v=", "");
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntetn = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getTrailer()));

                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntetn);
                }

            }
        });
        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        float radius = 10f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowsBackground = decorView.getBackground();

        binding.blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius);
        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

        if (item.getGenre() != null) {
            binding.genreView.setAdapter(new CategoryEachFilmAdapter(item.getGenre()));
            binding.genreView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }


        if (item.getCasts() != null) {
            binding.CastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.CastView.setAdapter(new CastListAdapter(item.getCasts()));
        }


    }
}