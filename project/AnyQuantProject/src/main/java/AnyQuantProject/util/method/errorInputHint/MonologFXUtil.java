package AnyQuantProject.util.method.errorInputHint;

public class MonologFXUtil {
	/**
	 * 只有一个‘确定’按钮的弹出框(用来显示消息提示而已)
	 * @param msg
	 * @return 返回点击的按钮 {@link MonologFXButton.Type}
	 */
	public static MonologFXButton.Type alert(final String msg) {
		MonologFX mono = MonologFXBuilder.create()
                .modal(true)
                .message(msg)
                .titleText("提示")
                .buttonAlignment(MonologFX.ButtonAlignment.CENTER)
                .build();

        MonologFXButton.Type type = mono.showDialog();
        return type;
	}
	
	/**
	 * 返回确认框(带有确定、取消按钮)
	 * @param msg
	 * @return 返回点击的按钮 {@link MonologFXButton.Type}
	 */
	public static MonologFXButton.Type confirm(final String msg) {
		MonologFX mono = MonologFXBuilder.create()
                .modal(true)
                .message(msg)
                .titleText("提示")
                .button(MonologFXButtonBuilder.create().type(MonologFXButton.Type.OK).build())
                .button(MonologFXButtonBuilder.create().type(MonologFXButton.Type.CANCEL).build())
                .buttonAlignment(MonologFX.ButtonAlignment.CENTER)
                .build();

        MonologFXButton.Type type = mono.showDialog();
        return type;
	}

    private MonologFXUtil() {
    }
	
}
