package org.tlauncher.tlauncherpe.mc.datalayer.db.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

open class ObjectAddonsEntity : RealmObject() {
    @PrimaryKey open var id: Int = 0
    open var name: String = ""
    open var type: TypeAddonsEntity = TypeAddonsEntity()
    open var category: CategoryAddonsEntity = CategoryAddonsEntity()
    open var text: String = ""
    open var date: String = ""
    open var views: String = ""
    open var downloads: String = ""
    open var file_url: String = ""
    open var file_size: String = ""
    open var imgs: RealmList<ImgAddonsEntity> = RealmList()
    //open var versions: Versions = null!!
    open var tags: RealmList<TagAddonsEntity> = RealmList()



}


open class CategoryAddonsEntity(): RealmObject() {

    constructor(type: Category) : this() {
        id = type.id ?: ""
        name = type.name ?: ""
    }

    open var id: String = ""
    open var name: String = ""
}

open class ImgAddonsEntity : RealmObject() {
    open var img: String = ""
}

open class TagAddonsEntity : RealmObject() {
    open var tag: String = ""
}

open class TypeAddonsEntity(): RealmObject(){

    constructor(type: Type) : this() {
        id = type.id
        name = type.name ?: ""
    }

    open var id: Int = 0
    open var name: String = ""
}
