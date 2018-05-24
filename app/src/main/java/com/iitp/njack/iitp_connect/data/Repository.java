package com.iitp.njack.iitp_connect.data;

import com.iitp.njack.iitp_connect.data.database.DatabaseRepository;
import com.iitp.njack.iitp_connect.data.network.ConnectionStatus;

import javax.inject.Inject;

import io.reactivex.Completable;
import lombok.experimental.Delegate;

/**
 * General Repository class. To be generified in future
 */
public final class Repository implements DatabaseRepository, ConnectionStatus {

    private final ContextUtils utilModel;
    private final ConnectionStatus connectionStatus;
    private final AbstractObservable abstractObservable;
    @Delegate
    private final DatabaseRepository databaseRepository;

    @Inject
    public Repository(ContextUtils utilModel, ConnectionStatus connectionStatus,
                      AbstractObservable abstractObservable, DatabaseRepository databaseRepository) {
        this.utilModel = utilModel;
        this.connectionStatus = connectionStatus;
        this.abstractObservable = abstractObservable;
        this.databaseRepository = databaseRepository;
    }
    // TODO: Fix this after implementing ROOM.
//    public <T, R> Completable syncSave(Class<T> clazz, List<T> items, Function<T, R> idMapper, Property<R> id) {
//        return Observable.fromIterable(items)
//            .map(idMapper::apply)
//            .toList()
//            .flatMapCompletable(ids -> databaseRepository.delete(clazz, id.notIn(ids)))
//            .concatWith(databaseRepository.saveList(clazz, items));
//    }

    public <T> AbstractObservable.AbstractObservableBuilder<T> observableOf(Class<T> clazz) {
        return abstractObservable.of(clazz);
    }

    public boolean isConnected() {
        return connectionStatus.isConnected();
    }

    public Completable deleteDatabase() {
        return utilModel.deleteDatabase();
    }
}
