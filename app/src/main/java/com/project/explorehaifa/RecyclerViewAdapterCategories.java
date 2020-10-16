package com.project.explorehaifa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.explorehaifa.model.Category;
import com.project.explorehaifa.model.CategoryItem;

import java.util.ArrayList;

public class RecyclerViewAdapterCategories extends RecyclerView.Adapter<RecyclerViewAdapterCategories.ViewHolder> {

    String TAG = "ImgUriInAdapter: ";
    private Category category;
    Context parentContext = null;
    //CategoryItem currentCategoryItem = null;


    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private ArrayList<TextView> listTextViews = new ArrayList<>();
        private Button btnFeedbacks = null;
        private Button btnShowOnMap = null;
        private Button btnShowStreet = null;
        private ImageView imgCategoryItem = null;
        private Button btnPhoneCall = null;
        private Button btnWebsite = null;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public ArrayList<TextView> getListTextViews() {
            return listTextViews;
        }

        public void setListTextViews(ArrayList<TextView> listTextViews) {
            this.listTextViews = listTextViews;
        }

        public Button getBtnFeedbacks() {
            return btnFeedbacks;
        }

        public void setBtnFeedbacks(Button btnFeedbacks) {
            this.btnFeedbacks = btnFeedbacks;
        }

        public Button getBtnShowOnMap() {
            return btnShowOnMap;
        }

        public void setBtnShowOnMap(Button btnShowOnMap) {
            this.btnShowOnMap = btnShowOnMap;
        }

        public Button getBtnShowStreet() {
            return btnShowStreet;
        }

        public void setBtnShowStreet(Button btnShowStreet) {
            this.btnShowStreet = btnShowStreet;
        }

        public ImageView getImgCategoryItem() {
            return imgCategoryItem;
        }

        public void setImgCategoryItem(ImageView imgCategoryItem) {
            this.imgCategoryItem = imgCategoryItem;
        }
        public Button getBtnPhoneCall() {
            return btnPhoneCall;
        }

        public void setBtnPhoneCall(Button btnPhoneCall) {
            this.btnPhoneCall = btnPhoneCall;
        }

        public Button getBtnWebsite() {
            return btnWebsite;
        }

        public void setBtnWebsite(Button btnWebsite) {
            this.btnWebsite = btnWebsite;
        }
    }

    public RecyclerViewAdapterCategories(Category category )
    {
        this.category = category;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_row,parent,false);

        parentContext = parent.getContext();
        //int numOfTextViews = this.category.getItems().get(0).getFieldsValues().size();
        int numOfTextViews = 10;

        LinearLayout rootView = new LinearLayout(parent.getContext());
        ViewGroup.LayoutParams layoutarams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(layoutarams);

        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)rootView.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin+50,
                params.rightMargin, params.bottomMargin+50);

        rootView.setOrientation(LinearLayout.VERTICAL);

//        if (this.category.get_defaultImageUri() != null)
//        {
//            String categoryImageUri = this.category.get_defaultImageUri();f
        ImageView categoryItemImage = new ImageView(parent.getContext());
//
//
//
//
//            Uri pictureUri = Uri.parse(categoryImageUri);
//            Glide.with(rootView.getContext()).load(pictureUri).into(categoryItemImage);
        rootView.addView(categoryItemImage);
//
        LinearLayout.LayoutParams paramsImage =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsImage.gravity =  Gravity.RIGHT;

        //tv.setBackgroundResource(Color.parseColor("#0000FF"));
//
//
        categoryItemImage.setLayoutParams(paramsImage);
        categoryItemImage.requestLayout();
        categoryItemImage.getLayoutParams().height = 600;
        categoryItemImage.getLayoutParams().width = 900;
        ViewGroup.MarginLayoutParams paramsImage2 =
                (ViewGroup.MarginLayoutParams)categoryItemImage.getLayoutParams();
        paramsImage2.setMargins(paramsImage2.leftMargin, paramsImage2.topMargin+5,
                paramsImage2.rightMargin, paramsImage2.bottomMargin+5);

//        }


        ArrayList<TextView> listTextViews = new ArrayList<>();
        if(this.category.get_type().equals("Manually"))
        {
            TextView tv = new TextView(parent.getContext());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            int textViewId = 100;
            tv.setId(textViewId);
            rootView.addView(tv);
            listTextViews.add(tv);

            LinearLayout.LayoutParams paramsTextView1 =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            paramsTextView1.gravity =  Gravity.RIGHT;
            //tv.setBackgroundResource(Color.parseColor("#0000FF"));


            tv.setLayoutParams(paramsTextView1);
            ViewGroup.MarginLayoutParams paramsTextView2 =
                    (ViewGroup.MarginLayoutParams)tv.getLayoutParams();
            paramsTextView2.setMargins(paramsTextView2.leftMargin, paramsTextView2.topMargin+20,
                    paramsTextView2.rightMargin, paramsTextView2.bottomMargin+10);

        }

        for (int i = 0; i < numOfTextViews; i++)
        {
            TextView tv = new TextView(parent.getContext());
            // tv.setAutoLinkMask(Linkify.ALL);
            Linkify.addLinks(tv,Linkify.WEB_URLS|Linkify.PHONE_NUMBERS);
            LinearLayout.LayoutParams paramsTextView1 =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsTextView1.gravity = Gravity.RIGHT;
            tv.setLayoutParams(paramsTextView1);
            ViewGroup.MarginLayoutParams paramsTextView2 =
                    (ViewGroup.MarginLayoutParams)tv.getLayoutParams();

            paramsTextView2.setMargins(paramsTextView2.leftMargin, paramsTextView2.topMargin+20,
                    paramsTextView2.rightMargin, paramsTextView2.bottomMargin+10);

            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            tv.setId(i);
            rootView.addView(tv);


            listTextViews.add(tv);
        }


        LinearLayout buttonsLayout = new LinearLayout(parent.getContext());
        ViewGroup.LayoutParams layoutParamsButtons =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonsLayout.setLayoutParams(layoutParamsButtons);

        ViewGroup.MarginLayoutParams buttonsLayoutMarginParms =
                (ViewGroup.MarginLayoutParams)buttonsLayout.getLayoutParams();
        buttonsLayoutMarginParms.setMargins(buttonsLayoutMarginParms.leftMargin,
                buttonsLayoutMarginParms.topMargin+50,
                buttonsLayoutMarginParms.rightMargin,
                buttonsLayoutMarginParms.bottomMargin+50);

        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button btnFeedbacks = new Button(parent.getContext());
        LinearLayout.LayoutParams paramsButton =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        //paramsButton.gravity = Gravity.RIGHT;
        btnFeedbacks.setLayoutParams(paramsButton);
        ViewGroup.MarginLayoutParams paramsButton2 =
                (ViewGroup.MarginLayoutParams)btnFeedbacks.getLayoutParams();
        paramsButton.setMargins(paramsButton2.leftMargin, paramsButton2.topMargin,
                paramsButton2.rightMargin, paramsButton2.bottomMargin);

        btnFeedbacks.setBackgroundResource(R.mipmap.feedback);
        //btnFeedbacks.setText("לחוות דעת");
        buttonsLayout.addView(btnFeedbacks);

        btnFeedbacks.requestLayout();
        btnFeedbacks.getLayoutParams().height = 130;
        btnFeedbacks.getLayoutParams().width= 130;
        Button btnShowOnMap = null;
        Button btnShowStreet = null;
        Button btnPhoneCall = null;
        Button btnWebsite = null;

        if(category.getIsShowOnMap().equals("true"))
        {

            btnShowOnMap = new Button(parent.getContext());
            LinearLayout.LayoutParams paramsButtonOnMap =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            //paramsButtonOnMap .gravity = Gravity.LEFT;

            btnShowOnMap.setBackgroundResource(R.mipmap.showonmap);
            // btnShowOnMap.setTooltipText("מפה");
            //    btnShowOnMap.setText("מפה");
            btnShowOnMap.setLayoutParams( paramsButtonOnMap );
            ViewGroup.MarginLayoutParams paramsButtonOnMap2 =
                    (ViewGroup.MarginLayoutParams)btnShowOnMap.getLayoutParams();
            paramsButtonOnMap.setMargins(paramsButtonOnMap2.leftMargin, paramsButtonOnMap2.topMargin,
                    paramsButtonOnMap2.rightMargin+70, paramsButtonOnMap2.bottomMargin);
            buttonsLayout.addView(btnShowOnMap);



            btnShowStreet = new Button(parent.getContext());
            LinearLayout.LayoutParams paramsShowStreet =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            // paramsShowStreet .gravity = Gravity.LEFT;

            btnShowStreet.setBackgroundResource(R.mipmap.streetviewicon);
            // btnShowStreet.setText("סייר");
            btnShowStreet.setLayoutParams( paramsShowStreet );
            ViewGroup.MarginLayoutParams paramsShowStreet2 =
                    (ViewGroup.MarginLayoutParams)btnShowStreet.getLayoutParams();
            paramsShowStreet.setMargins(paramsShowStreet2.leftMargin, paramsShowStreet2.topMargin,
                    paramsShowStreet2.rightMargin+70, paramsShowStreet2.bottomMargin);
            buttonsLayout.addView(btnShowStreet);

            btnShowStreet.requestLayout();
            btnShowStreet.getLayoutParams().height = 130;
            btnShowStreet.getLayoutParams().width= 130;

            btnShowOnMap.requestLayout();
            btnShowOnMap.getLayoutParams().height = 130;
            btnShowOnMap.getLayoutParams().width= 130;


        }

        btnPhoneCall = new Button(parent.getContext());
        LinearLayout.LayoutParams paramsphoneCall =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        // paramsShowStreet .gravity = Gravity.LEFT;

        btnPhoneCall.setBackgroundResource(R.mipmap.phonecallicon);
        btnPhoneCall.setVisibility(View.INVISIBLE);
        // btnShowStreet.setText("סייר");
        btnPhoneCall.setLayoutParams( paramsphoneCall );
        ViewGroup.MarginLayoutParams paramsphoneCall2 =
                (ViewGroup.MarginLayoutParams)btnPhoneCall.getLayoutParams();
        paramsphoneCall.setMargins(paramsphoneCall2.leftMargin, paramsphoneCall2.topMargin,
                paramsphoneCall2.rightMargin+70, paramsphoneCall2.bottomMargin);
        buttonsLayout.addView(btnPhoneCall);

        btnPhoneCall.requestLayout();
        btnPhoneCall.getLayoutParams().height = 130;
        btnPhoneCall.getLayoutParams().width= 130;

        btnWebsite = new Button(parent.getContext());
        LinearLayout.LayoutParams paramsWebsite =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        // paramsShowStreet .gravity = Gravity.LEFT;

        btnWebsite.setBackgroundResource(R.mipmap.websiteicon);
        btnWebsite.setVisibility(View.INVISIBLE);
        // btnShowStreet.setText("סייר");
        btnWebsite.setLayoutParams( paramsWebsite );
        ViewGroup.MarginLayoutParams paramsWebsite2 =
                (ViewGroup.MarginLayoutParams)btnWebsite.getLayoutParams();
        paramsWebsite.setMargins(paramsWebsite2.leftMargin, paramsWebsite2.topMargin,
                paramsWebsite2.rightMargin+70,paramsWebsite2.bottomMargin);
        buttonsLayout.addView(btnWebsite);

        btnWebsite.requestLayout();
        btnWebsite.getLayoutParams().height = 130;
        btnWebsite.getLayoutParams().width= 130;

        rootView.addView(buttonsLayout);
        ViewHolder svh = new ViewHolder(rootView);
        svh.setImgCategoryItem(categoryItemImage);
        for (int i = 0; i < numOfTextViews; i++)
        {
            svh.setListTextViews(listTextViews);
        }
        svh.setBtnFeedbacks(btnFeedbacks);
        if(category.getIsShowOnMap().equals("true"))
        {
            svh.setBtnShowOnMap(btnShowOnMap);
            svh.setBtnShowStreet(btnShowStreet);
        }
        svh.setBtnPhoneCall(btnPhoneCall);
        svh.setBtnWebsite(btnWebsite);

        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final Category currentCategory = this.category;
        //CategoryItem currentCategoryItem = this.category.getItems().get(position);
        CategoryItem currentCategoryItem = null;

        // Example:
        //                                        0         1         2        3           4        5
        //    Lets say, category.getItems():  invisible, invisible, VISIBLE, invisible invisible  VISIBLE
        //    So, we would like position to be:                        0                             1
        //    The inner if is executed only for the VISIBLE and countVisibleItems=1
        //    position is 0 (from the recyclerview)
        //    So, the currrent category item is set to  category.getItems().get(2)
        int countVisibleItems = 0;
        for (int i = 0; i < this.category.getItems().size(); i++) {
            if (this.category.getItems().get(i).isVisible()) {
                countVisibleItems++;
                if ((countVisibleItems - 1) == position) {
                    currentCategoryItem = this.category.getItems().get(i);
                    break;
                }
            }
        }

        String categoryDefaultImageUri = this.category.get_defaultImageUri();
        String categoryItemImageUri = currentCategoryItem.getPhotoUri();
        Uri pictureUri = null;
        // Log.d(TAG, "Item image uri before if:" + categoryItemImageUri);
        if (categoryItemImageUri != null) {
            Log.d(TAG, "Item image uri:" + categoryItemImageUri);
            pictureUri = Uri.parse(categoryItemImageUri);
            //  Log.d(TAG, "Item image uri and picture uri: " + categoryItemImageUri + " " + pictureUri );
        } else if (categoryDefaultImageUri != null) {
            pictureUri = Uri.parse(categoryDefaultImageUri);
        }

        holder.getImgCategoryItem().setImageDrawable(null);
        Glide.with(holder.getImgCategoryItem().getContext()).clear(holder.getImgCategoryItem());
        Glide.with(holder.itemView.getContext()).load(pictureUri).into(holder.getImgCategoryItem());


        Log.d(TAG, "after Glide. itemName=" + currentCategoryItem.get_name() + "  " + "pictureUri:" + pictureUri);


        int numOfFieldsInCurrentCategoryItem = currentCategoryItem.getFieldsValues().size();

        int i = 0;
        if (this.category.get_type().equals("Manually")) {
            TextView textView = holder.getListTextViews().get(i);
            String nameText = "<b>" +
                    "שם: " +
                    "</b>" +
                    currentCategoryItem.getCustomizedName();
            textView.setText(Html.fromHtml(nameText));

            textView.setTextColor(Color.parseColor("#FFFFFF"));
            i = 1;
            numOfFieldsInCurrentCategoryItem++;
        }
        int manuallycategoryItemFieldsNum = numOfFieldsInCurrentCategoryItem;
        int jsoncategoryItemFieldsNum = numOfFieldsInCurrentCategoryItem;
        Log.d(TAG, "the numberOf fields: " + numOfFieldsInCurrentCategoryItem);
        for (; i < numOfFieldsInCurrentCategoryItem; i++) {

            if (category.getFieldNamesForDisplay() != null) {

                if (category.getFieldNamesForDisplay().get(i).trim().equals("קישור"))

                {
                    jsoncategoryItemFieldsNum--;
                    String website =   currentCategoryItem.getFieldsValues().get(i);
                    holder.getBtnWebsite().setVisibility(View.VISIBLE);
                    holder.getBtnWebsite().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                            parentContext.startActivity(browserIntent);


                        }
                    });
                  // break;
            }  if (category.getFieldNamesForDisplay().get(i).trim().equals("טלפון")) {
                    String phoneNum = currentCategoryItem.getFieldsValues().get(i);
                    jsoncategoryItemFieldsNum--;
                    holder.getBtnPhoneCall().setVisibility(View.VISIBLE);
                    holder.getBtnPhoneCall().setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phoneNum));
                            parentContext.startActivity(intent);
                        }
                    });
                   // break;

            } else {
                    holder.getBtnWebsite().setVisibility(View.INVISIBLE);
                    holder.getBtnPhoneCall().setVisibility(View.INVISIBLE);
                    TextView textView = holder.getListTextViews().get(i);
                String rowText = "";
                rowText = "<b>" + category.getFieldNamesForDisplay().get(i) + ": " + "</b>" +
                        currentCategoryItem.getFieldsValues().get(i);
                textView.setText(Html.fromHtml(rowText));
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

            else{
                int flag = 0;
                 Log.d("fields names: ", currentCategoryItem.getFieldsNames().toString());
                String phoneNum = currentCategoryItem.getFieldsValues().get(i - 1);
                if (currentCategoryItem.getFieldsNames().get(i - 1).trim().equals("קישור")) {
                    manuallycategoryItemFieldsNum--;
                    String website = currentCategoryItem.getFieldsValues().get(i - 1);
                    Log.d("website: ", website);
                    flag = 1;
                    holder.getBtnWebsite().setVisibility(View.VISIBLE);
                    holder.getBtnWebsite().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                            parentContext.startActivity(browserIntent);

                        }
                    });
                    //  break;
                }  if (currentCategoryItem.getFieldsNames().get(i - 1).trim().equals("טלפון")) {
                   manuallycategoryItemFieldsNum--;
                    holder.getBtnPhoneCall().setVisibility(View.VISIBLE);
                    holder.getBtnPhoneCall().setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + phoneNum));
                            parentContext.startActivity(intent);
                        }
                    });
                        //  break;
                } else {
                    if (flag == 0 ){
                        holder.getBtnWebsite().setVisibility(View.INVISIBLE);
                        holder.getBtnPhoneCall().setVisibility(View.INVISIBLE);
                        TextView textView = holder.getListTextViews().get(i);
                        String rowText = "";
                        rowText = "<b>" + currentCategoryItem.getFieldsNames().get(i - 1) + ": " + "</b>" +
                                currentCategoryItem.getFieldsValues().get(i - 1);
                        textView.setText( Html.fromHtml(rowText) );
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                }
            }

        }

        if(category.get_type().equals( "JSON API")) {
            if (currentCategoryItem.getExtraFields() != null) {
                for (int k = 0; k < (currentCategoryItem.getExtraFields().size() / 2);  k=k+2) {


                    if (currentCategoryItem.getExtraFields().get(k) != null && currentCategoryItem.getExtraFields().get(k+1) != null) {

                        if (currentCategoryItem.getExtraFields().get(k).trim().equals("קישור")) {
                           // numOfFieldsInCurrentCategoryItem--;
                            String website =  currentCategoryItem.getExtraFields().get(k+1);
                            holder.getBtnWebsite().setVisibility(View.VISIBLE);
                            holder.getBtnWebsite().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                                    parentContext.startActivity(browserIntent);

                                }
                            });
                            break;
                        } else if (currentCategoryItem.getExtraFields().get(k).trim().equals("טלפון")) {
//                            numOfFieldsInCurrentCategoryItem--;
                            String phoneNum =  currentCategoryItem.getExtraFields().get(k+1);
                            holder.getBtnPhoneCall().setVisibility(View.VISIBLE);
                            holder.getBtnPhoneCall().setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + phoneNum));
                                    parentContext.startActivity(intent);
                                }
                            });
                            break;
                        } else {
                            holder.getBtnWebsite().setVisibility(View.INVISIBLE);
                            holder.getBtnPhoneCall().setVisibility(View.INVISIBLE);
                            TextView textView = holder.getListTextViews().get(i);
                            String rowText = "";
                            rowText = "<b>" + currentCategoryItem.getExtraFields().get(k)  + ": " + "</b>" +
                                    currentCategoryItem.getExtraFields().get(k+1);
                            Log.d("extraFields","extra field rowText: " + rowText);
                            textView.setText( Html.fromHtml(rowText) );

                            //textView.setText( rowText );
                            Linkify.addLinks(textView,Linkify.WEB_URLS|Linkify.PHONE_NUMBERS);
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                            jsoncategoryItemFieldsNum++;
                        }

                    }


                }

            }
        }
        if (this.category.get_type().equals("Manually")) {
            numOfFieldsInCurrentCategoryItem = manuallycategoryItemFieldsNum;
        }
        else
        {
            numOfFieldsInCurrentCategoryItem = jsoncategoryItemFieldsNum;
        }
        for (  i=numOfFieldsInCurrentCategoryItem;
               i < holder.getListTextViews().size(); i++ ) {
            TextView textView = holder.getListTextViews().get(i);
            textView.setVisibility(View.GONE);
            //int height = 1; //your textview height
            //textView.getLayoutParams().height = height;
        }
        final CategoryItem finalCurrentCategoryItem = currentCategoryItem;
        holder.getBtnFeedbacks().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityIntent = new Intent(parentContext, FeedbacksActivity.class);
                String categoryName = currentCategory.getName();
                String categoryItemCustomizedName = finalCurrentCategoryItem.getCustomizedName();

                activityIntent.putExtra("category name",categoryName );
                activityIntent.putExtra("category item customized name",categoryItemCustomizedName );
                //activityIntent.putExtra("category item name",categoryCustomizedItemName );
                parentContext.startActivity(activityIntent);
            }
        });
        final CategoryItem finalCurrentCategoryItem1 = currentCategoryItem;

        if(category.getIsShowOnMap().equals("true")) {
            holder.getBtnShowOnMap().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent activityIntent = new Intent(parentContext, MapsActivity.class);
                    String categoryName = currentCategory.getName();
                    String categoryItemCustomizedName = finalCurrentCategoryItem1.getCustomizedName();
                    String categoryItemName = finalCurrentCategoryItem.get_name();

                    //   activityIntent.putExtra("category name",categoryName );
                    activityIntent.putExtra("category item name",categoryItemName  );
                    //activityIntent.putExtra("category item name",categoryCustomizedItemName );
                    parentContext.startActivity(activityIntent);
                }
            });

            holder.getBtnShowStreet().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent activityIntent = new Intent(parentContext, StreetViewPanoramaBasicActivity.class);
                    String categoryName = currentCategory.getName();
                    String categoryItemCustomizedName = finalCurrentCategoryItem1.getCustomizedName();
                    String categoryItemName = finalCurrentCategoryItem.get_name();

                    //   activityIntent.putExtra("category name",categoryName );
                    activityIntent.putExtra("category item name", categoryItemName);
                    //activityIntent.putExtra("category item name",categoryCustomizedItemName );
                    parentContext.startActivity(activityIntent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        //return this.category.getItems().size();
        int count = 0;
        for( int i = 0;i < this.category.getItems().size();i++)
        {
            if(this.category.getItems().get(i).isVisible())
            {
                count++;
            }
        }
        return  count;
    }
}
