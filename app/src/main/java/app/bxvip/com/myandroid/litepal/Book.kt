package app.bxvip.com.myandroid.litepal

import org.litepal.crud.LitePalSupport

class Book(val name:String, val page:Int):LitePalSupport(){
    /**
     * 如果你的实体类中需要定义id这个字段，不要把它放到构造函数当中，因为id的值是由LitePal自动赋值的，而不应该由用户来指定。因此这里我们在Book类的内部声明了一个只读类型的id。
     */
    val id:Long = 0
}