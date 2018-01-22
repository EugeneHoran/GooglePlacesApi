package eugene.com.transferwise.util.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.transition.AutoTransition;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;

public class TransitionHelper extends TransitionSet {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initTransitionN(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementReturnTransition(new Transition());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementEnterTransition(new Transition());
            fragment.setEnterTransition(new Slide());
        }
    }

    private class Transition extends TransitionSet {
        private Transition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new AutoTransition());
        }
    }
}