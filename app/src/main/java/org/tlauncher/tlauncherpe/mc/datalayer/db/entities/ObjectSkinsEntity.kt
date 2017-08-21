package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

open class ObjectSkinsEntity : RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var type: TypeSkinsEntity = TypeSkinsEntity()
    open var category: CategorySkinsEntity = CategorySkinsEntity()
    open var text: String = ""
    open var date: String = ""
    open var views: String = ""
    open var downloads: String = ""
    open var file_url: String = ""
    open var file_size: String = ""
    open var imgs: RealmList<ImgSkinsEntity> = RealmList()
    //open var versions: Versions = null!!
    open var tags: RealmList<TagSkinsEntity> = RealmList()



}


open class CategorySkinsEntity(): RealmObject() {

    constructor(category: Category) : this() {
        id = category.id ?: ""
        name = category.name ?: ""
    }

    open var id: String = ""
    open var name: String = ""
}

open class ImgSkinsEntity : RealmObject() {
    open var img: String = ""
}

open class TagSkinsEntity : RealmObject() {
    open var tag: String = ""
}

open class TypeSkinsEntity(): RealmObject(){

    constructor(type: Type) : this() {
        id = type.id
        name = type.name ?: ""
    }

    open var id: Int = 0
    open var name: String = ""
}