package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldItemViewModel

data class ItemDetailsViewModel(val id : Int, val name : String?, val size : Long?, val numberOfDownloads : String?,
                                val description : String?, val downloadUrl : String?,
                                val images : List<String>?, val type : Type, val downloaded : Boolean, val date : String?) : ViewModel, Parcelable {
    val currentImage = ObservableField<String>()

    val progress = ObservableInt()

    val progressVisibility = ObservableBoolean()

    val buttonText = ObservableInt(R.string.download)

    val oneMB : Long = 1048576L

    init {
        if (images?.size ?: 0 > 0) {
            currentImage.set(images?.get(0))
        }
    }

    @Suppress("DEPRECATION")
    fun getItemDescription() : Spanned {
        val result : Spanned
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            result = Html.fromHtml(description)
        }
        return result
    }

    fun getItemSize() : String {
        if (size ?: 0 > oneMB) return String.format("%.2f MB", size?.div(Math.pow(1024.0, 2.0)))
        else return "$size KB"
    }

    enum class Type {
        Addon, Texture, World, Skin, Sid
    }

    companion object {
        fun map(item : WorldItemViewModel) : ItemDetailsViewModel {
            return ItemDetailsViewModel(item.id, item.name, item.fileSize, item.downloads, item.text, item.url,
                    item.image, Type.World, item.loaded, item.date)
        }

        fun map(item : AddonItemViewModel) : ItemDetailsViewModel {
            return ItemDetailsViewModel(item.id, item.name, item.fileSize, item.downloads, item.text, item.url,
                    item.image, Type.Addon, item.loaded, item.date)
        }

        fun map(item : TextureItemViewModel) : ItemDetailsViewModel {
            return ItemDetailsViewModel(item.id, item.name, item.fileSize, item.downloads, item.text, item.url,
                    item.image, Type.Texture, item.loaded, item.date)
        }

        fun map(item : SkinItemViewModel) : ItemDetailsViewModel {
            return ItemDetailsViewModel(item.id, item.name, item.fileSize, item.downloads, item.text, item.url,
                    item.image, Type.Skin, item.loaded, item.date)
        }

        fun map(item : SidItemViewModel) : ItemDetailsViewModel {
            return ItemDetailsViewModel(item.id, item.name, item.fileSize, item.downloads, item.text, item.url,
                    item.image, Type.Sid, item.loaded, item.date)
        }

        @JvmField val CREATOR : Parcelable.Creator<ItemDetailsViewModel> = object : Parcelable.Creator<ItemDetailsViewModel> {
            override fun createFromParcel(source : Parcel) : ItemDetailsViewModel = ItemDetailsViewModel(source)
            override fun newArray(size : Int) : Array<ItemDetailsViewModel?> = arrayOfNulls(size)
        }
    }

    constructor(source : Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            Type.values()[source.readInt()],
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest : Parcel, flags : Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeValue(size)
        dest.writeString(numberOfDownloads)
        dest.writeString(description)
        dest.writeString(downloadUrl)
        dest.writeStringList(images)
        dest.writeInt(type.ordinal)
        dest.writeInt((if (downloaded) 1 else 0))
        dest.writeString(date)
    }
}
