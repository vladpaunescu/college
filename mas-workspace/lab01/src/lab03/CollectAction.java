package lab03;

public class CollectAction implements Action {

	@Override
	public void execute(Context context) {
		context.collectObject();
	}

}
