package de.lolhens.sna.c

import java.nio.ByteBuffer

import jnr.ffi.types.size_t
import jnr.ffi.{LibraryLoader, Pointer}

object Main {
  def main(args: Array[String]): Unit = {
    println("test")

    val kernel32 = LibraryLoader.create(classOf[Kernel32]).load("kernel32")
    val user32 = LibraryLoader.create(classOf[User32]).load("user32")
    val runtime = jnr.ffi.Runtime.getRuntime(user32)
    val strPointer = Pointer.wrap(runtime, ByteBuffer.wrap("hello".getBytes))
    println("test2")

    user32.OpenClipboard(null)

    println("test3")

    //user32.EmptyClipboard()
    val handle = kernel32.allocateGlobal("test".getBytes)
    println(user32.EmptyClipboard())
    println(user32.SetClipboardData(1, handle))
    user32.CloseClipboard()
    println(user32.OpenClipboard(null)) //Pointer.wrap(runtime, 0)))
    //println(user32.EmptyClipboard())
    println("clipboard:")
    println(user32.GetClipboardData(1))
    println(user32.CloseClipboard())
  }

  val GMEM_MOVEABLE = 0x02
  val GMEM_ZEROINIT = 0x40

  case class Handle(address: Long) extends AnyVal

  trait Kernel32 {
    def allocateGlobal(data: Array[Byte]): Handle = {
      val handle = GlobalAlloc(GMEM_MOVEABLE, data.size + 1)
      val pointer = GlobalLock(handle)
      pointer.put(0L, data, 0, data.size)
      GlobalUnlock(handle)
      handle
    }

    def GetLastError(): String

    def GlobalAlloc(flags: Int, size: Long@size_t): Handle

    def GlobalLock(handle: Handle): Pointer

    def GlobalUnlock(handle: Handle): Int

    def GlobalFree(handle: Handle): Pointer
  }

  trait User32 {
    def OpenClipboard(hWndNewOwner: Pointer): Int

    //def openClipboard: Boolean = OpenClipboard(null) != 0
    def IsClipboardFormatAvailable(format: Int): Int

    def GetClipboardData(format: Int): String

    def EmptyClipboard(): Int

    def SetClipboardData(format: Int, handle: Handle): Pointer

    //def SetClipboardData(format: Int, string: String): Unit = SetClipboardData(format, )

    def CloseClipboard(): Int
  }
}
