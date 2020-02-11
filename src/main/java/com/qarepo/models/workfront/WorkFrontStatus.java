package com.qarepo.models.workfront;

public enum WorkFrontStatus {
    COMPLETED {
        public String toString() { return "CPL";};
    }, NEW {
        public String toString() { return "NEW";};
    };

}
