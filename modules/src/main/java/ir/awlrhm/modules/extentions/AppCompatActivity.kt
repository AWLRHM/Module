package ir.awlrhm.modules.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ir.awrhm.modules.R

fun AppCompatActivity.replaceFragmentInActivity(container: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.commit {
   /*     setCustomAnimations(
            R.anim.enter_anim,
            R.anim.exit_anim
        )*/
        replace(container, fragment, tag)
            .addToBackStack(tag)
    }
}

fun AppCompatActivity.addFragmentInActivity(container: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.commit {
        add(container, fragment, tag)
            .addToBackStack(tag)
    }
}

fun AppCompatActivity.configToolbar(toolbar: Toolbar) {
    this.setSupportActionBar(toolbar)
    toolbar.setTitleTextAppearance(this, R.style.ToolbarStyle)
}

