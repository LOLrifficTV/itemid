package main.java.com.lonelydime.ItemId;
/*
 * Portions of this software are Copyright (c) 2010 croemmich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
  -----------------------------------------------------------------------------
 * Remaining portions are (c) TexasGamer - Licensed under the TexasGamer Software License
 */
public class UpdateThread
implements Runnable
{
private boolean running = false;
private Thread thread;
private ItemId ids;

public UpdateThread(ItemId paramItemId)
{
  this.ids = paramItemId;
}

public void run() {
  while (this.running) {
    try {
      Thread.sleep(ItemId.autoUpdateInterval * 1000);
    } catch (InterruptedException localInterruptedException) {
    }
    this.ids.updateData();
  }
}

public void start() {
  this.running = true;
  this.thread = new Thread(this);
  this.thread.start();
}

public void stop() {
  this.running = false;
  this.thread.interrupt();
}
}