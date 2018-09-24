// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewPostActivity_ViewBinding implements Unbinder {
  private NewPostActivity target;

  private View view7f08007b;

  private View view7f08002d;

  @UiThread
  public NewPostActivity_ViewBinding(NewPostActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewPostActivity_ViewBinding(final NewPostActivity target, View source) {
    this.target = target;

    View view;
    target.newPostToolbar = Utils.findRequiredViewAsType(source, R.id.new_post_toolbar, "field 'newPostToolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.iv_new_post, "field 'ivNewPost' and method 'onImageClicked'");
    target.ivNewPost = Utils.castView(view, R.id.iv_new_post, "field 'ivNewPost'", ImageView.class);
    view7f08007b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImageClicked();
      }
    });
    target.etDescription = Utils.findRequiredViewAsType(source, R.id.et_description, "field 'etDescription'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_new_post, "field 'btnNewPost' and method 'onPostClicked'");
    target.btnNewPost = Utils.castView(view, R.id.btn_new_post, "field 'btnNewPost'", Button.class);
    view7f08002d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPostClicked();
      }
    });
    target.newPostProgress = Utils.findRequiredViewAsType(source, R.id.new_post_progress, "field 'newPostProgress'", ProgressBar.class);
    target.etTags = Utils.findRequiredViewAsType(source, R.id.et_tags, "field 'etTags'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewPostActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.newPostToolbar = null;
    target.ivNewPost = null;
    target.etDescription = null;
    target.btnNewPost = null;
    target.newPostProgress = null;
    target.etTags = null;

    view7f08007b.setOnClickListener(null);
    view7f08007b = null;
    view7f08002d.setOnClickListener(null);
    view7f08002d = null;
  }
}
