package shared.http;

import com.google.gwt.core.shared.GwtIncompatible;

public interface HttpContent {

    int getLength();

    String getText();

    @GwtIncompatible
    byte[] getBytes();

}
