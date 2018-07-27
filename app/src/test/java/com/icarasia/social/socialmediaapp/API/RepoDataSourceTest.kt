package com.icarasia.social.socialmediaapp.API

import android.util.Log
import com.icarasia.social.socialmediaapp.Comments.Comment
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.Posts.Post
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoInitializationException
import org.mockito.internal.configuration.injection.MockInjection
import kotlin.properties.Delegates

class RepoDataSourceTest {

    private var remoteDataSource = Mockito.spy( RemoteDataSource())
    private var localDataSource = Mockito.spy(LocalDataSource())

    private val repoDataSourceTest : RepoDataSource = RepoDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxUnitTestingSetup.run()

        repoDataSourceTest.remoteDS = remoteDataSource
        repoDataSourceTest.localDS = localDataSource
    }


    @Test
    fun `getCommetsForPost when exist in Cash`(){
        var post = Post(id = 1)
        var comments = ArrayList<Comment>()
        comments.add(Comment())



        //Mockito.doReturn(Observable.fromArray(comments)).`when`(repoDataSourceTest.localDS).getCommetsForPost(post.id)

        //Mockito.doReturn(true).`when`(localDataSource).contains("${post.id}")

        //repoDataSourceTest.getCommetsForPost(post.id)

        //repoDataSourceTest.localDS.getCommetsForPost(post.id).subscribe { print(it) }


        //Mockito.verify(repoDataSourceTest.localDS).getCommetsForPost(post.id)

    }


    @Test
    fun `delete post test verify All`(){

        Mockito.doReturn(Observable.fromArray(Post(id = 1))).`when`(repoDataSourceTest.remoteDS).deletePosts(1)

        repoDataSourceTest.deletePosts(1)

        // local
        Mockito.verify(repoDataSourceTest.localDS).clearCash()

        //remote
        repoDataSourceTest.remoteDS.deletePosts(1).subscribe { assert(it.id==1) }

        //observable
        var test = repoDataSourceTest.remoteDS.deletePosts(1).test()
        test.assertComplete()
    }

    @Test
    fun `creat post test verify local call`(){

        Mockito.doReturn(Observable.fromArray(Post(id = 1))).`when`(repoDataSourceTest.remoteDS).createPost(Post(id=1))

        repoDataSourceTest.createPost(Post(id = 1))

        // local
        Mockito.verify(repoDataSourceTest.localDS).clearCash()

        //remote
        repoDataSourceTest.remoteDS.createPost(Post(id = 1)).subscribe { assert(it.id==1) }

        //observable
        var test = repoDataSourceTest.remoteDS.createPost(Post(id = 1)).test()
        test.assertComplete()

    }

    @Test
    fun `get User Remote All Test`(){
        var users = ArrayList<User>()
        users.add(User(1))

        Mockito.doReturn(Observable.fromArray(users)).`when`(repoDataSourceTest.remoteDS).getUser("Bret")

        repoDataSourceTest.getUser("Bret")

        //local....Nothing to be called

        //remote
        Mockito.verify(repoDataSourceTest.remoteDS).getUser("Bret")
        repoDataSourceTest.remoteDS.getUser("Bret").subscribe { assert(it[0].id==1) }

        //Observable
        var test = repoDataSourceTest.remoteDS.getUser("Bret").test()
        test.assertComplete()

    }


    @Test
    fun `get Albums Remote Call Test`(){
        var albums = ArrayList<Any>()
        albums.add(Any())

        Mockito.doReturn(Observable.fromArray(albums)).`when`(repoDataSourceTest.remoteDS).getAlbums(1)

        repoDataSourceTest.remoteDS.getAlbums(1)

        //local....Nothing to be called

        //remote
        Mockito.verify(repoDataSourceTest.remoteDS).getAlbums(1)
        repoDataSourceTest.remoteDS.getAlbums(1).subscribe { assert(it.size>0) }

        //Observable
        var test = repoDataSourceTest.remoteDS.getAlbums(1).test()
        test.assertComplete()

    }

    @Test
    fun `get Todos Remote Call Test`(){
        var todos = ArrayList<Any>()
        todos.add(Any())

        Mockito.doReturn(Observable.fromArray(todos)).`when`(repoDataSourceTest.remoteDS).getTodos(1)

        repoDataSourceTest.remoteDS.getTodos(1)

        //local....Nothing to be called

        //remote
        Mockito.verify(repoDataSourceTest.remoteDS).getTodos(1)
        repoDataSourceTest.remoteDS.getTodos(1).subscribe { assert(it.size>0) }

        //Observable
        var test = repoDataSourceTest.remoteDS.getTodos(1).test()
        test.assertComplete()

    }


}