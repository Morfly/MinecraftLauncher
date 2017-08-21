package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

open class ObjectsTexturesEntity : RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var type: TypeTexturesEntity = TypeTexturesEntity()
    open var category: CategoryTexturesEntity = CategoryTexturesEntity()
    open var text: String = ""
    open var date: String = ""
    open var views: String = ""
    open var downloads: String = ""
    open var file_url: String = ""
    open var file_size: String = ""
    open var imgs: RealmList<ImgTexturesEntity> = RealmList()
    //open var versions: Versions = null!!
    open var tags: RealmList<TagTexturesEntity> = RealmList()



}


open class CategoryTexturesEntity(): RealmObject() {

    constructor(category: Category) : this() {
        id = category.id ?: ""
        name = category.name ?: ""
    }

    open var id: String = ""
    open var name: String = ""
}

open class ImgTexturesEntity : RealmObject() {
    open var img: String = ""
}

open class TagTexturesEntity : RealmObject() {
    open var tag: String = ""
}

open class TypeTexturesEntity(): RealmObject(){

    constructor(type: Type) : this() {
        id = type.id
        name = type.name ?: ""
    }

    open var id: Int = 0
    open var name: String = ""
}