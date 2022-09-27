package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl(private val dao: PostDao) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) = dao.save(post.toEntity())

    override fun like(id: Long) = dao.likeById(id)

    override fun share(id: Long) = dao.share(id)
//       val post = dao.getAll().value?.let { entities ->
//           entities.map {
//               if (it.id != id) it.toModel()
//               else it.toModel().copy(
//                   shares = it.shares + 1
//               )
//           }
//       }

    override fun delete(id: Long) = dao.removeById(id)

}