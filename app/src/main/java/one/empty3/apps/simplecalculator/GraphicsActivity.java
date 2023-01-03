package one.empty3.apps.simplecalculator;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class GraphicsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics);
        getIntent().getExtras().get("formula");
        getIntent().getExtras().get("xMin");
        getIntent().getExtras().get("xMax");
    }
}
