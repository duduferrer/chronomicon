package bh.app.chronomicon.model.enums;

import java.time.Duration;

public enum WorkloadOperation {
    ADD{
        @Override
        public Duration apply(Duration current, Duration delta){
            return current.plus (delta);
        }
    },
    SUBTRACT{
        @Override
        public Duration apply(Duration current, Duration delta){
            Duration result = current.minus (delta);
            return result.isNegative () ? Duration.ZERO : result;
        }

    },
    RESET{
        @Override
        public Duration apply(Duration current, Duration delta){
            return Duration.ZERO;
        }
    };

    public abstract Duration apply(Duration current, Duration delta);
}
