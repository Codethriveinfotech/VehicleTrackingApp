package com.vehicletrackingapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vehicletrackingapp.data.model.Driver;
import com.vehicletrackingapp.data.model.MaintenanceRecord;
import com.vehicletrackingapp.data.model.TripEntry;
import com.vehicletrackingapp.data.model.Vehicle;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TripEntry> __insertionAdapterOfTripEntry;

  private final EntityInsertionAdapter<MaintenanceRecord> __insertionAdapterOfMaintenanceRecord;

  private final EntityInsertionAdapter<Vehicle> __insertionAdapterOfVehicle;

  private final EntityInsertionAdapter<Driver> __insertionAdapterOfDriver;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVehicle;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDriver;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTripEntry = new EntityInsertionAdapter<TripEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trips` (`id`,`driverId`,`vehicleId`,`startDate`,`startTime`,`startOdometer`,`startOdometerPhotoUri`,`startVehiclePhotoUri`,`startVehiclePlatePhotoUri`,`endDate`,`endTime`,`endOdometer`,`endOdometerPhotoUri`,`endVehiclePhotoUri`,`endVehiclePlatePhotoUri`,`sourceLocation`,`destinationLocation`,`fuelLevel`,`tripPurpose`,`notes`,`status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntry entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDriverId());
        if (entity.getVehicleId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getVehicleId());
        }
        statement.bindString(4, entity.getStartDate());
        statement.bindString(5, entity.getStartTime());
        statement.bindString(6, entity.getStartOdometer());
        if (entity.getStartOdometerPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getStartOdometerPhotoUri());
        }
        if (entity.getStartVehiclePhotoUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getStartVehiclePhotoUri());
        }
        if (entity.getStartVehiclePlatePhotoUri() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getStartVehiclePlatePhotoUri());
        }
        statement.bindString(10, entity.getEndDate());
        statement.bindString(11, entity.getEndTime());
        statement.bindString(12, entity.getEndOdometer());
        if (entity.getEndOdometerPhotoUri() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getEndOdometerPhotoUri());
        }
        if (entity.getEndVehiclePhotoUri() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getEndVehiclePhotoUri());
        }
        if (entity.getEndVehiclePlatePhotoUri() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getEndVehiclePlatePhotoUri());
        }
        statement.bindString(16, entity.getSourceLocation());
        statement.bindString(17, entity.getDestinationLocation());
        statement.bindString(18, entity.getFuelLevel());
        statement.bindString(19, entity.getTripPurpose());
        statement.bindString(20, entity.getNotes());
        statement.bindString(21, entity.getStatus());
      }
    };
    this.__insertionAdapterOfMaintenanceRecord = new EntityInsertionAdapter<MaintenanceRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `maintenance` (`id`,`vehicleId`,`driverId`,`maintenanceType`,`description`,`date`,`time`,`cost`,`serviceNotes`,`billImageUri`,`status`,`oilChangeDone`,`tyreStatusOk`,`batteryStatusOk`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MaintenanceRecord entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVehicleId());
        statement.bindString(3, entity.getDriverId());
        statement.bindString(4, entity.getMaintenanceType());
        statement.bindString(5, entity.getDescription());
        statement.bindString(6, entity.getDate());
        statement.bindString(7, entity.getTime());
        statement.bindString(8, entity.getCost());
        statement.bindString(9, entity.getServiceNotes());
        if (entity.getBillImageUri() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getBillImageUri());
        }
        statement.bindString(11, entity.getStatus());
        final int _tmp = entity.getOilChangeDone() ? 1 : 0;
        statement.bindLong(12, _tmp);
        final int _tmp_1 = entity.getTyreStatusOk() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        final int _tmp_2 = entity.getBatteryStatusOk() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
      }
    };
    this.__insertionAdapterOfVehicle = new EntityInsertionAdapter<Vehicle>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vehicles` (`id`,`number`,`model`,`imageUri`,`assignedDriverId`,`type`,`registrationNumber`,`fuelType`,`status`,`mileage`,`insuranceStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Vehicle entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNumber());
        statement.bindString(3, entity.getModel());
        if (entity.getImageUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getImageUri());
        }
        if (entity.getAssignedDriverId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAssignedDriverId());
        }
        statement.bindString(6, entity.getType());
        statement.bindString(7, entity.getRegistrationNumber());
        statement.bindString(8, entity.getFuelType());
        statement.bindString(9, entity.getStatus());
        statement.bindString(10, entity.getMileage());
        statement.bindString(11, entity.getInsuranceStatus());
      }
    };
    this.__insertionAdapterOfDriver = new EntityInsertionAdapter<Driver>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `drivers` (`id`,`name`,`phone`,`licenseNumber`,`password`,`photoUri`,`email`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Driver entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        statement.bindString(4, entity.getLicenseNumber());
        statement.bindString(5, entity.getPassword());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhotoUri());
        }
        statement.bindString(7, entity.getEmail());
      }
    };
    this.__preparedStmtOfDeleteVehicle = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vehicles WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteDriver = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM drivers WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsertTrip(final TripEntry trip, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTripEntry.insert(trip);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertMaintenance(final MaintenanceRecord record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMaintenanceRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertVehicle(final Vehicle vehicle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVehicle.insert(vehicle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertDriver(final Driver driver, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDriver.insert(driver);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVehicle(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVehicle.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteVehicle.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDriver(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDriver.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteDriver.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TripEntry> getDraftTrip(final String driverId) {
    final String _sql = "SELECT * FROM trips WHERE driverId = ? AND status = 'draft' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, driverId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<TripEntry>() {
      @Override
      @Nullable
      public TripEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDriverId = CursorUtil.getColumnIndexOrThrow(_cursor, "driverId");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfStartOdometer = CursorUtil.getColumnIndexOrThrow(_cursor, "startOdometer");
          final int _cursorIndexOfStartOdometerPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startOdometerPhotoUri");
          final int _cursorIndexOfStartVehiclePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startVehiclePhotoUri");
          final int _cursorIndexOfStartVehiclePlatePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startVehiclePlatePhotoUri");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfEndOdometer = CursorUtil.getColumnIndexOrThrow(_cursor, "endOdometer");
          final int _cursorIndexOfEndOdometerPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endOdometerPhotoUri");
          final int _cursorIndexOfEndVehiclePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endVehiclePhotoUri");
          final int _cursorIndexOfEndVehiclePlatePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endVehiclePlatePhotoUri");
          final int _cursorIndexOfSourceLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceLocation");
          final int _cursorIndexOfDestinationLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationLocation");
          final int _cursorIndexOfFuelLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelLevel");
          final int _cursorIndexOfTripPurpose = CursorUtil.getColumnIndexOrThrow(_cursor, "tripPurpose");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final TripEntry _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDriverId;
            _tmpDriverId = _cursor.getString(_cursorIndexOfDriverId);
            final String _tmpVehicleId;
            if (_cursor.isNull(_cursorIndexOfVehicleId)) {
              _tmpVehicleId = null;
            } else {
              _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            }
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpStartOdometer;
            _tmpStartOdometer = _cursor.getString(_cursorIndexOfStartOdometer);
            final String _tmpStartOdometerPhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartOdometerPhotoUri)) {
              _tmpStartOdometerPhotoUri = null;
            } else {
              _tmpStartOdometerPhotoUri = _cursor.getString(_cursorIndexOfStartOdometerPhotoUri);
            }
            final String _tmpStartVehiclePhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartVehiclePhotoUri)) {
              _tmpStartVehiclePhotoUri = null;
            } else {
              _tmpStartVehiclePhotoUri = _cursor.getString(_cursorIndexOfStartVehiclePhotoUri);
            }
            final String _tmpStartVehiclePlatePhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartVehiclePlatePhotoUri)) {
              _tmpStartVehiclePlatePhotoUri = null;
            } else {
              _tmpStartVehiclePlatePhotoUri = _cursor.getString(_cursorIndexOfStartVehiclePlatePhotoUri);
            }
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpEndOdometer;
            _tmpEndOdometer = _cursor.getString(_cursorIndexOfEndOdometer);
            final String _tmpEndOdometerPhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndOdometerPhotoUri)) {
              _tmpEndOdometerPhotoUri = null;
            } else {
              _tmpEndOdometerPhotoUri = _cursor.getString(_cursorIndexOfEndOdometerPhotoUri);
            }
            final String _tmpEndVehiclePhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndVehiclePhotoUri)) {
              _tmpEndVehiclePhotoUri = null;
            } else {
              _tmpEndVehiclePhotoUri = _cursor.getString(_cursorIndexOfEndVehiclePhotoUri);
            }
            final String _tmpEndVehiclePlatePhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndVehiclePlatePhotoUri)) {
              _tmpEndVehiclePlatePhotoUri = null;
            } else {
              _tmpEndVehiclePlatePhotoUri = _cursor.getString(_cursorIndexOfEndVehiclePlatePhotoUri);
            }
            final String _tmpSourceLocation;
            _tmpSourceLocation = _cursor.getString(_cursorIndexOfSourceLocation);
            final String _tmpDestinationLocation;
            _tmpDestinationLocation = _cursor.getString(_cursorIndexOfDestinationLocation);
            final String _tmpFuelLevel;
            _tmpFuelLevel = _cursor.getString(_cursorIndexOfFuelLevel);
            final String _tmpTripPurpose;
            _tmpTripPurpose = _cursor.getString(_cursorIndexOfTripPurpose);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _result = new TripEntry(_tmpId,_tmpDriverId,_tmpVehicleId,_tmpStartDate,_tmpStartTime,_tmpStartOdometer,_tmpStartOdometerPhotoUri,_tmpStartVehiclePhotoUri,_tmpStartVehiclePlatePhotoUri,_tmpEndDate,_tmpEndTime,_tmpEndOdometer,_tmpEndOdometerPhotoUri,_tmpEndVehiclePhotoUri,_tmpEndVehiclePlatePhotoUri,_tmpSourceLocation,_tmpDestinationLocation,_tmpFuelLevel,_tmpTripPurpose,_tmpNotes,_tmpStatus);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TripEntry>> getSubmittedTrips() {
    final String _sql = "SELECT * FROM trips WHERE status = 'submitted' ORDER BY startDate DESC, startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<List<TripEntry>>() {
      @Override
      @NonNull
      public List<TripEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDriverId = CursorUtil.getColumnIndexOrThrow(_cursor, "driverId");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfStartOdometer = CursorUtil.getColumnIndexOrThrow(_cursor, "startOdometer");
          final int _cursorIndexOfStartOdometerPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startOdometerPhotoUri");
          final int _cursorIndexOfStartVehiclePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startVehiclePhotoUri");
          final int _cursorIndexOfStartVehiclePlatePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "startVehiclePlatePhotoUri");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfEndOdometer = CursorUtil.getColumnIndexOrThrow(_cursor, "endOdometer");
          final int _cursorIndexOfEndOdometerPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endOdometerPhotoUri");
          final int _cursorIndexOfEndVehiclePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endVehiclePhotoUri");
          final int _cursorIndexOfEndVehiclePlatePhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "endVehiclePlatePhotoUri");
          final int _cursorIndexOfSourceLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceLocation");
          final int _cursorIndexOfDestinationLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "destinationLocation");
          final int _cursorIndexOfFuelLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelLevel");
          final int _cursorIndexOfTripPurpose = CursorUtil.getColumnIndexOrThrow(_cursor, "tripPurpose");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<TripEntry> _result = new ArrayList<TripEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripEntry _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDriverId;
            _tmpDriverId = _cursor.getString(_cursorIndexOfDriverId);
            final String _tmpVehicleId;
            if (_cursor.isNull(_cursorIndexOfVehicleId)) {
              _tmpVehicleId = null;
            } else {
              _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            }
            final String _tmpStartDate;
            _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpStartOdometer;
            _tmpStartOdometer = _cursor.getString(_cursorIndexOfStartOdometer);
            final String _tmpStartOdometerPhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartOdometerPhotoUri)) {
              _tmpStartOdometerPhotoUri = null;
            } else {
              _tmpStartOdometerPhotoUri = _cursor.getString(_cursorIndexOfStartOdometerPhotoUri);
            }
            final String _tmpStartVehiclePhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartVehiclePhotoUri)) {
              _tmpStartVehiclePhotoUri = null;
            } else {
              _tmpStartVehiclePhotoUri = _cursor.getString(_cursorIndexOfStartVehiclePhotoUri);
            }
            final String _tmpStartVehiclePlatePhotoUri;
            if (_cursor.isNull(_cursorIndexOfStartVehiclePlatePhotoUri)) {
              _tmpStartVehiclePlatePhotoUri = null;
            } else {
              _tmpStartVehiclePlatePhotoUri = _cursor.getString(_cursorIndexOfStartVehiclePlatePhotoUri);
            }
            final String _tmpEndDate;
            _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpEndOdometer;
            _tmpEndOdometer = _cursor.getString(_cursorIndexOfEndOdometer);
            final String _tmpEndOdometerPhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndOdometerPhotoUri)) {
              _tmpEndOdometerPhotoUri = null;
            } else {
              _tmpEndOdometerPhotoUri = _cursor.getString(_cursorIndexOfEndOdometerPhotoUri);
            }
            final String _tmpEndVehiclePhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndVehiclePhotoUri)) {
              _tmpEndVehiclePhotoUri = null;
            } else {
              _tmpEndVehiclePhotoUri = _cursor.getString(_cursorIndexOfEndVehiclePhotoUri);
            }
            final String _tmpEndVehiclePlatePhotoUri;
            if (_cursor.isNull(_cursorIndexOfEndVehiclePlatePhotoUri)) {
              _tmpEndVehiclePlatePhotoUri = null;
            } else {
              _tmpEndVehiclePlatePhotoUri = _cursor.getString(_cursorIndexOfEndVehiclePlatePhotoUri);
            }
            final String _tmpSourceLocation;
            _tmpSourceLocation = _cursor.getString(_cursorIndexOfSourceLocation);
            final String _tmpDestinationLocation;
            _tmpDestinationLocation = _cursor.getString(_cursorIndexOfDestinationLocation);
            final String _tmpFuelLevel;
            _tmpFuelLevel = _cursor.getString(_cursorIndexOfFuelLevel);
            final String _tmpTripPurpose;
            _tmpTripPurpose = _cursor.getString(_cursorIndexOfTripPurpose);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new TripEntry(_tmpId,_tmpDriverId,_tmpVehicleId,_tmpStartDate,_tmpStartTime,_tmpStartOdometer,_tmpStartOdometerPhotoUri,_tmpStartVehiclePhotoUri,_tmpStartVehiclePlatePhotoUri,_tmpEndDate,_tmpEndTime,_tmpEndOdometer,_tmpEndOdometerPhotoUri,_tmpEndVehiclePhotoUri,_tmpEndVehiclePlatePhotoUri,_tmpSourceLocation,_tmpDestinationLocation,_tmpFuelLevel,_tmpTripPurpose,_tmpNotes,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<MaintenanceRecord> getDraftMaintenance(final String driverId) {
    final String _sql = "SELECT * FROM maintenance WHERE driverId = ? AND status = 'draft' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, driverId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"maintenance"}, new Callable<MaintenanceRecord>() {
      @Override
      @Nullable
      public MaintenanceRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfDriverId = CursorUtil.getColumnIndexOrThrow(_cursor, "driverId");
          final int _cursorIndexOfMaintenanceType = CursorUtil.getColumnIndexOrThrow(_cursor, "maintenanceType");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfServiceNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceNotes");
          final int _cursorIndexOfBillImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "billImageUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfOilChangeDone = CursorUtil.getColumnIndexOrThrow(_cursor, "oilChangeDone");
          final int _cursorIndexOfTyreStatusOk = CursorUtil.getColumnIndexOrThrow(_cursor, "tyreStatusOk");
          final int _cursorIndexOfBatteryStatusOk = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryStatusOk");
          final MaintenanceRecord _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final String _tmpDriverId;
            _tmpDriverId = _cursor.getString(_cursorIndexOfDriverId);
            final String _tmpMaintenanceType;
            _tmpMaintenanceType = _cursor.getString(_cursorIndexOfMaintenanceType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final String _tmpCost;
            _tmpCost = _cursor.getString(_cursorIndexOfCost);
            final String _tmpServiceNotes;
            _tmpServiceNotes = _cursor.getString(_cursorIndexOfServiceNotes);
            final String _tmpBillImageUri;
            if (_cursor.isNull(_cursorIndexOfBillImageUri)) {
              _tmpBillImageUri = null;
            } else {
              _tmpBillImageUri = _cursor.getString(_cursorIndexOfBillImageUri);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpOilChangeDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOilChangeDone);
            _tmpOilChangeDone = _tmp != 0;
            final boolean _tmpTyreStatusOk;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTyreStatusOk);
            _tmpTyreStatusOk = _tmp_1 != 0;
            final boolean _tmpBatteryStatusOk;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfBatteryStatusOk);
            _tmpBatteryStatusOk = _tmp_2 != 0;
            _result = new MaintenanceRecord(_tmpId,_tmpVehicleId,_tmpDriverId,_tmpMaintenanceType,_tmpDescription,_tmpDate,_tmpTime,_tmpCost,_tmpServiceNotes,_tmpBillImageUri,_tmpStatus,_tmpOilChangeDone,_tmpTyreStatusOk,_tmpBatteryStatusOk);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<MaintenanceRecord>> getSubmittedMaintenance() {
    final String _sql = "SELECT * FROM maintenance WHERE status = 'submitted' ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"maintenance"}, new Callable<List<MaintenanceRecord>>() {
      @Override
      @NonNull
      public List<MaintenanceRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfDriverId = CursorUtil.getColumnIndexOrThrow(_cursor, "driverId");
          final int _cursorIndexOfMaintenanceType = CursorUtil.getColumnIndexOrThrow(_cursor, "maintenanceType");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfServiceNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceNotes");
          final int _cursorIndexOfBillImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "billImageUri");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfOilChangeDone = CursorUtil.getColumnIndexOrThrow(_cursor, "oilChangeDone");
          final int _cursorIndexOfTyreStatusOk = CursorUtil.getColumnIndexOrThrow(_cursor, "tyreStatusOk");
          final int _cursorIndexOfBatteryStatusOk = CursorUtil.getColumnIndexOrThrow(_cursor, "batteryStatusOk");
          final List<MaintenanceRecord> _result = new ArrayList<MaintenanceRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MaintenanceRecord _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final String _tmpDriverId;
            _tmpDriverId = _cursor.getString(_cursorIndexOfDriverId);
            final String _tmpMaintenanceType;
            _tmpMaintenanceType = _cursor.getString(_cursorIndexOfMaintenanceType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final String _tmpCost;
            _tmpCost = _cursor.getString(_cursorIndexOfCost);
            final String _tmpServiceNotes;
            _tmpServiceNotes = _cursor.getString(_cursorIndexOfServiceNotes);
            final String _tmpBillImageUri;
            if (_cursor.isNull(_cursorIndexOfBillImageUri)) {
              _tmpBillImageUri = null;
            } else {
              _tmpBillImageUri = _cursor.getString(_cursorIndexOfBillImageUri);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpOilChangeDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfOilChangeDone);
            _tmpOilChangeDone = _tmp != 0;
            final boolean _tmpTyreStatusOk;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTyreStatusOk);
            _tmpTyreStatusOk = _tmp_1 != 0;
            final boolean _tmpBatteryStatusOk;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfBatteryStatusOk);
            _tmpBatteryStatusOk = _tmp_2 != 0;
            _item = new MaintenanceRecord(_tmpId,_tmpVehicleId,_tmpDriverId,_tmpMaintenanceType,_tmpDescription,_tmpDate,_tmpTime,_tmpCost,_tmpServiceNotes,_tmpBillImageUri,_tmpStatus,_tmpOilChangeDone,_tmpTyreStatusOk,_tmpBatteryStatusOk);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Vehicle>> getAllVehicles() {
    final String _sql = "SELECT * FROM vehicles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vehicles"}, new Callable<List<Vehicle>>() {
      @Override
      @NonNull
      public List<Vehicle> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfAssignedDriverId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedDriverId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRegistrationNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationNumber");
          final int _cursorIndexOfFuelType = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelType");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
          final int _cursorIndexOfInsuranceStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "insuranceStatus");
          final List<Vehicle> _result = new ArrayList<Vehicle>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Vehicle _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNumber;
            _tmpNumber = _cursor.getString(_cursorIndexOfNumber);
            final String _tmpModel;
            _tmpModel = _cursor.getString(_cursorIndexOfModel);
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final String _tmpAssignedDriverId;
            if (_cursor.isNull(_cursorIndexOfAssignedDriverId)) {
              _tmpAssignedDriverId = null;
            } else {
              _tmpAssignedDriverId = _cursor.getString(_cursorIndexOfAssignedDriverId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpRegistrationNumber;
            _tmpRegistrationNumber = _cursor.getString(_cursorIndexOfRegistrationNumber);
            final String _tmpFuelType;
            _tmpFuelType = _cursor.getString(_cursorIndexOfFuelType);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpMileage;
            _tmpMileage = _cursor.getString(_cursorIndexOfMileage);
            final String _tmpInsuranceStatus;
            _tmpInsuranceStatus = _cursor.getString(_cursorIndexOfInsuranceStatus);
            _item = new Vehicle(_tmpId,_tmpNumber,_tmpModel,_tmpImageUri,_tmpAssignedDriverId,_tmpType,_tmpRegistrationNumber,_tmpFuelType,_tmpStatus,_tmpMileage,_tmpInsuranceStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Driver>> getAllDrivers() {
    final String _sql = "SELECT * FROM drivers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"drivers"}, new Callable<List<Driver>>() {
      @Override
      @NonNull
      public List<Driver> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfLicenseNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "licenseNumber");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final List<Driver> _result = new ArrayList<Driver>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Driver _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpLicenseNumber;
            _tmpLicenseNumber = _cursor.getString(_cursorIndexOfLicenseNumber);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _item = new Driver(_tmpId,_tmpName,_tmpPhone,_tmpLicenseNumber,_tmpPassword,_tmpPhotoUri,_tmpEmail);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findDriver(final String identity, final String password,
      final Continuation<? super Driver> $completion) {
    final String _sql = "SELECT * FROM drivers WHERE (name = ? OR phone = ?) AND password = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, identity);
    _argIndex = 2;
    _statement.bindString(_argIndex, identity);
    _argIndex = 3;
    _statement.bindString(_argIndex, password);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Driver>() {
      @Override
      @Nullable
      public Driver call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfLicenseNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "licenseNumber");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final Driver _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpLicenseNumber;
            _tmpLicenseNumber = _cursor.getString(_cursorIndexOfLicenseNumber);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result = new Driver(_tmpId,_tmpName,_tmpPhone,_tmpLicenseNumber,_tmpPassword,_tmpPhotoUri,_tmpEmail);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
