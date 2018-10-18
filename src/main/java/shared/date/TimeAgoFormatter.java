package shared.date;

import util.shared.collection.Lazy;

import java.util.Date;

public class TimeAgoFormatter {

    private static Lazy<TimeFormat[]> TimeFormats = Lazy.of(() -> new TimeFormat[] {
        new TimeFormat(60, "seconds",1), // 60
        new TimeFormat(120,"1 minute ago","1 minute from now"), // 60*2
        new TimeFormat(3600,"minutes",60), // 60*60, 60
        new TimeFormat(7200,"1 hour ago","1 hour from now"), // 60*60*2
        new TimeFormat(86400,"hours",3600), // 60*60*24, 60*60
        new TimeFormat(172800,"Yesterday","Tomorrow"), // 60*60*24*2
        new TimeFormat(604800,"days",86400), // 60*60*24*7, 60*60*24
        new TimeFormat(1209600,"Last week","Next week"), // 60*60*24*7*4*2
        new TimeFormat(2419200,"weeks",604800), // 60*60*24*7*4, 60*60*24*7
        new TimeFormat(4838400,"Last month","Next month"), // 60*60*24*7*4*2
        new TimeFormat(29030400,"months",2419200), // 60*60*24*7*4*12, 60*60*24*7*4
        new TimeFormat(58060800,"Last year","Next year"), // 60*60*24*7*4*12*2
        new TimeFormat(2903040000L,"years",29030400), // 60*60*24*7*4*12*100, 60*60*24*7*4*12
        new TimeFormat(5806080000L,"Last century","Next century"), // 60*60*24*7*4*12*100*2
        new TimeFormat(58060800000L,"centuries",2903040000L) // 60*60*24*7*4*12*100*20, 60*60*24*7*4*12*100
    });

    public static String format(Date date){
        long seconds = (new Date().getTime() - date.getTime()) / 1000;
        boolean isFuture = false;

        String token = "ago";
        if (seconds == 0){
            return "Just now";
        }
        if (seconds < 0) {
            seconds = Math.abs(seconds);
            token = "from now";
            isFuture = true;
        }

        for (int i = 0, total = TimeFormats.get().length; i < total; i++) {
            TimeFormat format = TimeFormats.get()[i];
            if (seconds < format.seconds) {
                if (isFuture && format.future.length() > 0) {
                    return format.future;
                }
                if (format.unit > 0){
                    return String.valueOf(Math.floor(seconds / format.unit)) + ' ' + format.unitText + ' ' + token;
                }
            }
        }
        return date.toString();
    }

    private static class TimeFormat {

        private long seconds;
        private long unit;
        private String unitText;
        private String future;

        public TimeFormat(long seconds, String unitText, long unit) {
            this.seconds = seconds;
            this.unit = unit;
            this.unitText = unitText;
            this.future = "";
        }

        public TimeFormat(long seconds, String unitText, String future) {
            this.seconds = seconds;
            this.unit = 0;
            this.unitText = unitText;
            this.future = future;
        }
    }
}
