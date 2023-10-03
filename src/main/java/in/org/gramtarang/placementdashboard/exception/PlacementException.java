package in.org.gramtarang.placementdashboard.exception;

public class PlacementException extends RuntimeException{
    public PlacementException() {
    }

    public PlacementException(String message) {
        super(message);
    }

    public PlacementException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlacementException(Throwable cause) {
        super(cause);
    }

    public PlacementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
