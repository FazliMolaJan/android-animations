package com.tbuonomo.androidanimations.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.config.GlideApp;
import com.tbuonomo.androidanimations.view.adapter.item.NatureItem;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import java.util.List;

/**
 * Created by tommy on 06/09/17.
 */

public class NatureItemsAdapter extends RecyclerView.Adapter<NatureItemsAdapter.ViewHolder> {
  private List<NatureItem> natureItems;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public NatureItemsAdapter(List<NatureItem> natureItems) {
    this.natureItems = natureItems;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nature, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    int drawableResId = natureItems.get(position).getDrawableResId();
    holder.natureImage.setTransitionName(String.valueOf(drawableResId));

    // Need to override size to avoid glide to reload drawable on exit shared element transition
    GlideApp.with(context)
        .load(drawableResId)
        .override((int) DimenUtils.getScreenWidth(context), (int) DimenUtils.toDp(context, 400))
        .into(holder.natureImage);

    holder.natureImage.setParallaxStyles(new VerticalMovingStyle());

    holder.itemView.setOnClickListener(view -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClick(natureItems.get(position), holder.natureImage);
      }
    });
  }

  @Override public int getItemCount() {
    return natureItems.size();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onItemClick(NatureItem natureItem, View natureView);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_nature_image) public ScrollParallaxImageView natureImage;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
