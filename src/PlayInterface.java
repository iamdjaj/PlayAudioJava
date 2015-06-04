public interface PlayInterface {
  public void init(Object parent, String fileName) throws Exception;
  public void play() throws Exception;

  public void play(Object parent, String fileName) throws Exception;
  public void stop();
  public void dispose(); 
}
