// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CommentsActivity_ViewBinding implements Unbinder {
  private CommentsActivity target;

  private View view7f080041;

  @UiThread
  public CommentsActivity_ViewBinding(CommentsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CommentsActivity_ViewBinding(final CommentsActivity target, View source) {
    this.target = target;

    View view;
    target.commentsToolbar = Utils.findRequiredViewAsType(source, R.id.comments_toolbar, "field 'commentsToolbar'", Toolbar.class);
    target.commentsList = Utils.findRequiredViewAsType(source, R.id.comments_list, "field 'commentsList'", RecyclerView.class);
    target.commentsField = Utils.findRequiredViewAsType(source, R.id.comments_field, "field 'commentsField'", EditText.class);
    view = Utils.findRequiredView(source, R.id.comments_sent, "field 'commentsSent' and method 'onViewClicked'");
    target.commentsSent = Utils.castView(view, R.id.comments_sent, "field 'commentsSent'", ImageView.class);
    view7f080041 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.tvComments = Utils.findRequiredViewAsType(source, R.id.tv_comments, "field 'tvComments'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CommentsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.commentsToolbar = null;
    target.commentsList = null;
    target.commentsField = null;
    target.commentsSent = null;
    target.tvComments = null;

    view7f080041.setOnClickListener(null);
    view7f080041 = null;
  }
}
