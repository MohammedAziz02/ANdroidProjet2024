package com.projetjava.Adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.projetjava.Activities.RecipeActivity;
import com.projetjava.Models.Recipe;
import com.projetjava.R;
import java.util.List;

// c'est pour la view d'accueil càd on affiche seulement l'image et le title de recipe
public class RecipeBaseAdapter extends BaseAdapter {
    private Context mContext;
    private List<Recipe> mData;

    public RecipeBaseAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /// on fait inflater le view anisi que ajouter l'evenemeent de click si on veut afficher la recipe
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            // on fait inflater dans le fichier xml qui existe dans le fichier res/layout
            view = layoutInflater.inflate(R.layout.cardview_recipe, parent, false);
            final MyHolder myHolder = new MyHolder(view);
            view.setTag(myHolder);
        } else {
            view = convertView;
        }
        MyHolder myHolder = (MyHolder) view.getTag();
        myHolder.recipeTitle.setText(mData.get(position).getName_recipe());
        myHolder.recipeType.setText(mData.get(position).getType());
        myHolder.recipeDifficulty.setText(mData.get(position).getDifficulty());
        myHolder.img_recipe_thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(mData.get(position).getImageBytes(), 0, mData.get(position).getImageBytes().length));

        view.setOnClickListener(v -> {
            System.out.println("wa akhoya clicked");
            System.out.println("data name "+ mData.get(position).getId());
            System.out.println("data name "+ mData.get(position).getName_recipe());
            System.out.println("data ingredients "+ mData.get(position).getIngredients_recipe());
            System.out.println("date description "+mData.get(position).getDescription_recipe());
            System.out.println("data image "+mData.get(position).getImageBytes());
            Intent intent = new Intent(mContext, RecipeActivity.class);
            // i made this line to resolve the problem
            //Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("RecipeId", mData.get(position).getId());
            intent.putExtra("RecipeName", mData.get(position).getName_recipe());
            intent.putExtra("RecipeType", mData.get(position).getType());
            intent.putExtra("RecipeDifficulty", mData.get(position).getDifficulty());
            intent.putExtra("RecipeIngredients", mData.get(position).getIngredients_recipe());
            intent.putExtra("RecipeMethodTitle", mData.get(position).getDescription_recipe());
            intent.putExtra("ImageBytes", mData.get(position).getImageBytes());

            mContext.startActivity(intent);
        });
        return view;
    }

    public class MyHolder {
        TextView recipeTitle;
        TextView recipeType;
        TextView recipeDifficulty;
        ImageView img_recipe_thumbnail;
        public MyHolder(View itemView) {
            recipeTitle = itemView.findViewById(R.id.recipe_text);
            recipeType = itemView.findViewById(R.id.recipe_type);
            recipeDifficulty = itemView.findViewById(R.id.recipe_difficulty);
            img_recipe_thumbnail = itemView.findViewById(R.id.recipe_img_id);
        }
    }
}

