package ojass20.nitjsr.in.ojass.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.islamkhsh.CardSliderAdapter;
import com.github.islamkhsh.CardSliderViewPager;

import java.util.ArrayList;


import ojass20.nitjsr.in.ojass.R;

public class GurugyanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gurugyan);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(R.drawable.messi, 1, getString(R.string.gurugyan_1_title), getString(R.string.gurugyan_1_description)));
        cards.add(new Card(R.drawable.ronaldo, 2, getString(R.string.gurugyan_2_title), getString(R.string.gurugyan_2_description)));
        cards.add(new Card(R.drawable.neymar, 3, getString(R.string.gurugyan_3_title), getString(R.string.gurugyan_3_description)));

        CardSliderViewPager viewPager = (CardSliderViewPager) findViewById(R.id.gurugyan_viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(cards));

    }

    private class Card {
        int backgroundResource;
        int day;
        String title;
        String description;

        public Card(int backgroundResource, int day, String title, String description) {
            this.backgroundResource = backgroundResource;
            this.day = day;
            this.title = title;
            this.description = description;
        }
    }

    private class ViewPagerAdapter extends CardSliderAdapter<Card> {

        public ViewPagerAdapter(ArrayList<Card> items) {
            super(items);
        }

        @Override
        public void bindView(int i, View view, Card card) {
            ImageView background = (ImageView) view.findViewById(R.id.gurugyan_card_background);
            TextView day = (TextView) view.findViewById(R.id.gurugyan_card_day);
            TextView title = (TextView) view.findViewById(R.id.gurugyan_card_title);
            TextView description = (TextView) view.findViewById(R.id.gurugyan_card_description);

            background.setImageResource(card.backgroundResource);
            day.setText("Day " + card.day);
            title.setText(card.title);
            description.setText(card.description);
        }

        @Override
        public int getItemContentLayout(int i) {
            return R.layout.gurugyan_item;
        }
    }
    
}
