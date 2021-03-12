package com.example.learn_demo.lock

import android.util.Log
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.random.Random

/**
 * @author vianhuang
 * @date 2021/3/12 5:44 PM
 */
class ReadWriteLockTest {
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()
    private var value = 0

    fun test() {
        simpleTest()
    }

    fun simpleTest() {
        Thread(writeRunnable).start()
        Thread(writeRunnable).start()
        Thread(readRunnable).start()
        Thread(readRunnable).start()
        Thread(readRunnable).start()
    }

    private val writeRunnable = Runnable {
        for (i in 1..5) {
            writeLock.lock()
            value++
            Log.d("vian", "[${Thread.currentThread().name}] write $value.")
            writeLock.unlock()
        }
    }

    private val readRunnable = Runnable {
        for (i in 1..5) {
            readLock.lock()
            Log.d("vian", "[${Thread.currentThread().name}] read $value")
            readLock.unlock()
        }
    }


    /**
     * 可以锁降级
     * 线程获取写入锁后可以获取读取锁，然后释放写入锁，这样就从写入锁变成了读取锁，从而实现锁降级的特性
     */
    private fun downgradeLock() {
        writeLock.lock()
        Log.d("vian", "lock write lock success.")
        readLock.lock()
        Log.d("vian", "lock read lock success.")

        writeLock.unlock()
        Log.d("vian", "unlock write lock success.")
        readLock.unlock()
        Log.d("vian", "unlock read lock success.")
    }


    /**
     * 不可锁升级
     * 线程获取写入锁后可以获取读取锁，然后释放写入锁，这样就从写入锁变成了读取锁，从而实现锁降级的特性
     */
    private fun upgradeLock() {
        readLock.lock()
        Log.d("vian", "lock read lock success.")
        writeLock.lock()
        Log.d("vian", "lock write lock success.")

        readLock.unlock()
        Log.d("vian", "unlock read lock success.")
        writeLock.unlock()
        Log.d("vian", "unlock write lock success.")
    }
}