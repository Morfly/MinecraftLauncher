package org.tlauncher.tlauncherpe.mc.datalayer.db.mapper

import io.realm.RealmList
import io.realm.RealmModel
import org.tlauncher.tlauncherpe.mc.datalayer.db.entities.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*

inline fun <T : RealmModel?> Array<T>.toRealmList() = io.realm.RealmList<T>(*this)
inline fun <reified T : RealmModel?> List<T>.toRealmList() = io.realm.RealmList<T>(*(this.toTypedArray()))

fun Objects.toEntity(): ObjectsEntity {
    val entity = ObjectsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = TypeEntity(this.type!!)
    entity.category = CategoryEntity(this.category!!)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> ImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> TagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    return entity
}

fun ObjectsEntity.toModel(): Objects =
        Objects(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag })

fun MyMods.toEntity(): MyModsEntity {
    val entity = MyModsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = MyTypeEntity(this.type)
    entity.category = MyCategoryEntity(this.category)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> MyImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> MyTagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    entity.filePath = this.filePath ?: ""
    return entity
}

fun MyModsEntity.toModel(): MyMods =
        MyMods(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag },
                filePath)



fun Skins.toEntity(): ObjectSkinsEntity {
    val entity = ObjectSkinsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = TypeSkinsEntity(this.type!!)
    entity.category = CategorySkinsEntity(this.category!!)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> ImgSkinsEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> TagSkinsEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    return entity
}

fun ObjectSkinsEntity.toModel(): Skins =
        Skins(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag })

fun MySkins.toEntity(): MySkinsEntity {
    val entity = MySkinsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = MySkinsTypeEntity(this.type)
    entity.category = MySkinsCategoryEntity(this.category)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> MySkinsImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> MySkinsTagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    entity.filePath = this.filePath ?: ""
    entity.isImported = this.isImported ?: false
    return entity
}

fun MySkinsEntity.toModel(): MySkins =
        MySkins(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag },
                filePath,
                isImported)

fun Textures.toEntity(): ObjectsTexturesEntity {
    val entity = ObjectsTexturesEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = TypeTexturesEntity(this.type!!)
    entity.category = CategoryTexturesEntity(this.category!!)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> ImgTexturesEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> TagTexturesEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    return entity
}

fun ObjectsTexturesEntity.toModel(): Textures =
        Textures(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag })

fun MyTextures.toEntity(): MyTexturesEntity {
    val entity = MyTexturesEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = MyTexturesTypeEntity(this.type)
    entity.category = MyTexturesCategoryEntity(this.category)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> MyTexturesImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> MyTexturesTagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    entity.filePath = this.filePath ?: ""
    entity.isImported = this.isImported ?: false
    return entity
}

fun MyTexturesEntity.toModel(): MyTextures =
        MyTextures(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag },
                filePath,
                isImported)

fun Addons.toEntity(): ObjectAddonsEntity {
    val entity = ObjectAddonsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = TypeAddonsEntity(this.type!!)
    entity.category = CategoryAddonsEntity(this.category!!)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> ImgAddonsEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> TagAddonsEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    return entity
}

fun ObjectAddonsEntity.toModel(): Addons =
        Addons(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag })

fun MyAddons.toEntity(): MyAddonsEntity {
    val entity = MyAddonsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = MyAddonsTypeEntity(this.type)
    entity.category = MyAddonsCategoryEntity(this.category)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> MyAddonsImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> MyAddonsTagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    entity.filePath = this.filePath ?: ""
    entity.isImported = this.isImported ?: false
    return entity
}

fun MyAddonsEntity.toModel(): MyAddons =
        MyAddons(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag },
                filePath,
                isImported)

fun Sids.toEntity(): ObjectSidsEntity {
    val entity = ObjectSidsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = TypeSidsEntity(this.type!!)
    entity.category = CategorySidsEntity(this.category!!)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> ImgSidsEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> TagSidsEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    return entity
}

fun ObjectSidsEntity.toModel(): Sids =
        Sids(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag })

fun MySids.toEntity(): MySidsEntity {
    val entity = MySidsEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.type = MySidsTypeEntity(this.type)
    entity.category = MySidsCategoryEntity(this.category)
    entity.text = this.text ?: ""
    entity.date = this.date ?: ""
    entity.views = this.views ?: ""
    entity.downloads = this.downloads ?: ""
    entity.file_url = this.file_url ?: ""
    entity.file_size = this.file_size ?: ""
    entity.imgs = this.imgs?.map { img -> MySidsImgEntity().apply { this.img = img } }?.toRealmList() ?: RealmList()
    //entity.versions = this.versions ?: ""
    entity.tags = this.tags?.map { tag -> MySidsTagEntity().apply { this.tag = tag } }?.toRealmList() ?: RealmList()
    entity.filePath = this.filePath ?: ""
    return entity
}

fun MySidsEntity.toModel(): MySids =
        MySids(id,
                name,
                Type(type.id,type.name),
                Category(category.id,category.name),
                text,
                date,
                views,
                downloads,
                file_url,
                file_size,
                imgs.map { it.img },
                null,
                tags.map { it.tag },
                filePath)

fun Servers.toEntity(): ServersEntity {
    val entity = ServersEntity()
    entity.id = this.id
    entity.name = this.name ?: ""
    entity.text = this.text ?: ""
    entity.ip = this.ip ?: ""
    entity.rank = this.rank
    //entity.versions = this.versions
    return entity
}

fun ServersEntity.toModel(): Servers =
        Servers(id,
                name,
                text,
                ip,
                rank,
                null)