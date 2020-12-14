package ir.awlrhm.modules.log;

public class AWLRHMNotInitializedException extends RuntimeException {
  public AWLRHMNotInitializedException() {
    super("Initialize Sherlock using Sherlock.init(context) before using its methods");
  }
}
