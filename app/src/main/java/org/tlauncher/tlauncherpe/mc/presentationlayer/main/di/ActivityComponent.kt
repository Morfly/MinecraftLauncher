package org.tlauncher.tlauncherpe.mc.presentationlayer.main.di

import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.ActivityScope
import org.tlauncher.tlauncherpe.mc.datalayer.db.DatabaseModule
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.di.RepositoryModule
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.di.AddonComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.di.AllCategoryComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.di.ExplorerComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.di.ExplorerModul
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.di.ServerComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.di.ServerModul
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.di.SidComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.di.SkinComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.di.TextureComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.di.WorldComponent

@ActivityScope
@Subcomponent(modules = arrayOf(MainModuls::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ServerModul::class,
        ExplorerModul::class))
interface ActivityComponent {
    @Subcomponent.Builder
    interface Builder {
        fun activityModule(activityModule : MainModuls) : Builder
        fun activityModule(serverModul : ServerModul) : Builder
        fun activityModule(explorerModul: ExplorerModul): Builder
        fun build() : ActivityComponent
    }

    fun plusWorldComponent() : WorldComponent.Builder

    val databaseManager : DbManager

    fun plusAddonComponent() : AddonComponent.Builder

    fun plusTextureComponent() : TextureComponent.Builder

    fun plusSkinComponent(): SkinComponent.Builder

    fun plusSidComponent(): SidComponent.Builder

    fun plusServerComponent() : ServerComponent.Builder

    fun plusExplorerComponent() : ExplorerComponent.Builder

    fun plusAllCategoryComponent() : AllCategoryComponent.Builder
}