module com.iwm.schedule_engine {
    requires static lombok;
    requires com.fasterxml.jackson.databind;


    exports com.iwm.schedule_engine.engine;
    exports com.iwm.schedule_engine.models.dtos;

    opens com.iwm.schedule_engine.configurations to com.fasterxml.jackson.databind;
    opens com.iwm.schedule_engine.configurations.businessconfigs to com.fasterxml.jackson.databind;
}