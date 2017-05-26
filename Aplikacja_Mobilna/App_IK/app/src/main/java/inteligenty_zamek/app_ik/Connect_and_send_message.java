package inteligenty_zamek.app_ik;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class Connect_and_send_message extends AsyncTask<Object, Object, Boolean> {

    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter mBluetoothAdapter;
    private Bluetooth_maneger bluetooth_maneger = null;
    private String address;
    private String data;

    public Connect_and_send_message(String address, String data) {
        this.address = address;
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetooth_maneger == null) {
            bluetooth_maneger = new Bluetooth_maneger(mHandler);
            mOutStringBuffer = new StringBuffer("");
        }
    }


    @Override
    protected Boolean doInBackground(Object... params) {
        if (bluetooth_maneger != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (bluetooth_maneger.getState() == Bluetooth_maneger.STATE_NONE) {
                // Start the Bluetooth chat services
                bluetooth_maneger.start();
            }
        }

        connectDevice(true);
        while(bluetooth_maneger.getState() != Bluetooth_maneger.STATE_CONNECTED){}
        sendMessage(data);
        while(bluetooth_maneger.getState() != Bluetooth_maneger.MESSAGE_SENDED){}
        return true;
    }

    private void connectDevice(boolean secure) {
        // Get the device MAC address
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        bluetooth_maneger.connect(device, secure);
    }

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (bluetooth_maneger.getState() != Bluetooth_maneger.STATE_CONNECTED) {
            // TODO informacja o bledzie polaczenia
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            bluetooth_maneger.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case 2:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    break;
            }
        }
    };

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        if (bluetooth_maneger != null) {
            bluetooth_maneger.stop();
        }
    }
}
