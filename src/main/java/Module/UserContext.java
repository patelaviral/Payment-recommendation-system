package Module;

public class UserContext {
    private DeviceContext deviceContext;
    public UserContext(DeviceContext deviceContext) {
        this.deviceContext = deviceContext;
    }
    public DeviceContext getDeviceContext() {
        return deviceContext;
    }
}
