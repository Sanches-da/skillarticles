package ru.skillbranch.skillarticles.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.layout_bottombar.*
import kotlinx.android.synthetic.main.layout_submenu.*
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.viewmodels.ArticleState
import ru.skillbranch.skillarticles.viewmodels.ArticleViewModel
import ru.skillbranch.skillarticles.viewmodels.Notify
import ru.skillbranch.skillarticles.viewmodels.ViewModelFactory

class RootActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setupToolbar()
        setupBottomBar()
        setupSubmenu()

        val  vmFactory = ViewModelFactory("0")
        viewModel = ViewModelProviders.of(this, vmFactory).get(ArticleViewModel::class.java)
        viewModel.observeState(this){
            renderUi(it)
        }

        viewModel.observeNotifications(this){
            renderNotification(it)
        }

    }

    private fun renderNotification(notify: Notify) {
        val snackbar = Snackbar.make(coordinator_container, notify.message, Snackbar.LENGTH_LONG)
            .setAnchorView(bottombar)

        when (notify){
            is Notify.TextMessage -> {}
            is Notify.ActionMessage -> {
                with(snackbar) {
                    setBackgroundTint(getColor(R.color.color_accent_dark))
                    setAction(notify.actionLabel) {
                        notify.actionHandler.invoke()
                    }
                }
            }
            is Notify.ErrorMessage -> {
                with(snackbar) {
                    setBackgroundTint(getColor(R.color.design_default_color_error))
                    setTextColor(getColor(android.R.color.white))
                    setActionTextColor(getColor(android.R.color.white))
                    setAction(notify.errLabel) {
                        notify.errHandler?.invoke()
                    }
                }
            }
        }

        snackbar.show()
    }

    private fun setupSubmenu() {
        btn_text_up.setOnClickListener{viewModel.handleUpText()}
        btn_text_down.setOnClickListener{viewModel.handleDownText()}
        switch_mode.setOnClickListener{viewModel.handleNightMode()}
    }

    private fun setupBottomBar() {
        btn_like.setOnClickListener{viewModel.handleLike()}
        btn_bookmark.setOnClickListener{viewModel.handleBookmark()}
        btn_share.setOnClickListener{viewModel.handleShare()}
        btn_settings.setOnClickListener{viewModel.handleToggleMenu()}

    }

    private fun renderUi(data: ArticleState) {
        btn_settings.isChecked = data.isShowMenu
        if(data.isShowMenu) submenu.open() else submenu.close()

        btn_like.isChecked = data.isLike
        btn_bookmark.isChecked = data.isBookmark

        switch_mode.isChecked = data.isDarkMode
        delegate.localNightMode = if(data.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        btn_text_up.isChecked = data.isBigText
        btn_text_down.isChecked = !data.isBigText
        tv_text_content.textSize = if (data.isBigText) 18f else 14f
        tv_text_content.text = if (data.isLoadingContent) "loading" else data.content.first() as String

        toolbar.title = data.title ?: "loading"
        toolbar.subtitle = data.category ?: "loading"
        if (data.categoryIcon != null) toolbar.logo = getDrawable(data.categoryIcon as Int)

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val logo = if(toolbar.childCount > 2) toolbar.getChildAt(2) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP

        val lp = logo?.layoutParams as? Toolbar.LayoutParams
        lp?.let{
            it.width = this.dpToIntPx(40)
            it.height = this.dpToIntPx(40)
            it.marginEnd = this.dpToIntPx(16)
            logo.layoutParams = it
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Введите строку поиска"

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                viewModel.handleSearchMode(true)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                //item?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
                viewModel.handleSearchMode(false)
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleSearch(newText)
                return true
            }

        })

        val state = viewModel.currentState

        if (state.isSearch) {
            searchItem.expandActionView()

            searchView.apply {
                setQuery(state.searchQuery, true)
            }

        }


        return super.onCreateOptionsMenu(menu)
    }

}
