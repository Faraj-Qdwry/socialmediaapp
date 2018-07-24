package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import org.junit.Before

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoInitializationException
import org.mockito.internal.configuration.injection.MockInjection

class RepoDataSourceTest {

    @Mock lateinit var remoteDataSource : RemoteDataSource
    @Mock lateinit var localDataSource: LocalDataSource

    private val repoDataSourceTest : RepoDataSource = RepoDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxUnitTestingSetup.run()

        repoDataSourceTest.remoteDS = remoteDataSource
        repoDataSourceTest.localDS = localDataSource
    }





}