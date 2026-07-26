package com.vehicletrackingapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AppDao _appDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` TEXT NOT NULL, `driverId` TEXT NOT NULL, `vehicleId` TEXT, `startDate` TEXT NOT NULL, `startTime` TEXT NOT NULL, `startOdometer` TEXT NOT NULL, `startOdometerPhotoUri` TEXT, `startVehiclePhotoUri` TEXT, `startVehiclePlatePhotoUri` TEXT, `endDate` TEXT NOT NULL, `endTime` TEXT NOT NULL, `endOdometer` TEXT NOT NULL, `endOdometerPhotoUri` TEXT, `endVehiclePhotoUri` TEXT, `endVehiclePlatePhotoUri` TEXT, `sourceLocation` TEXT NOT NULL, `destinationLocation` TEXT NOT NULL, `fuelLevel` TEXT NOT NULL, `tripPurpose` TEXT NOT NULL, `notes` TEXT NOT NULL, `status` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `maintenance` (`id` TEXT NOT NULL, `vehicleId` TEXT NOT NULL, `driverId` TEXT NOT NULL, `maintenanceType` TEXT NOT NULL, `description` TEXT NOT NULL, `date` TEXT NOT NULL, `time` TEXT NOT NULL, `cost` TEXT NOT NULL, `serviceNotes` TEXT NOT NULL, `billImageUri` TEXT, `status` TEXT NOT NULL, `oilChangeDone` INTEGER NOT NULL, `tyreStatusOk` INTEGER NOT NULL, `batteryStatusOk` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vehicles` (`id` TEXT NOT NULL, `number` TEXT NOT NULL, `model` TEXT NOT NULL, `imageUri` TEXT, `assignedDriverId` TEXT, `type` TEXT NOT NULL, `registrationNumber` TEXT NOT NULL, `fuelType` TEXT NOT NULL, `status` TEXT NOT NULL, `mileage` TEXT NOT NULL, `insuranceStatus` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `drivers` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `phone` TEXT NOT NULL, `licenseNumber` TEXT NOT NULL, `password` TEXT NOT NULL, `photoUri` TEXT, `email` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd663f9789d9a75af37711f430f07f219')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `trips`");
        db.execSQL("DROP TABLE IF EXISTS `maintenance`");
        db.execSQL("DROP TABLE IF EXISTS `vehicles`");
        db.execSQL("DROP TABLE IF EXISTS `drivers`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTrips = new HashMap<String, TableInfo.Column>(21);
        _columnsTrips.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("driverId", new TableInfo.Column("driverId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("vehicleId", new TableInfo.Column("vehicleId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startOdometer", new TableInfo.Column("startOdometer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startOdometerPhotoUri", new TableInfo.Column("startOdometerPhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startVehiclePhotoUri", new TableInfo.Column("startVehiclePhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startVehiclePlatePhotoUri", new TableInfo.Column("startVehiclePlatePhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endOdometer", new TableInfo.Column("endOdometer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endOdometerPhotoUri", new TableInfo.Column("endOdometerPhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endVehiclePhotoUri", new TableInfo.Column("endVehiclePhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endVehiclePlatePhotoUri", new TableInfo.Column("endVehiclePlatePhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("sourceLocation", new TableInfo.Column("sourceLocation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("destinationLocation", new TableInfo.Column("destinationLocation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("fuelLevel", new TableInfo.Column("fuelLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("tripPurpose", new TableInfo.Column("tripPurpose", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrips = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrips = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrips = new TableInfo("trips", _columnsTrips, _foreignKeysTrips, _indicesTrips);
        final TableInfo _existingTrips = TableInfo.read(db, "trips");
        if (!_infoTrips.equals(_existingTrips)) {
          return new RoomOpenHelper.ValidationResult(false, "trips(com.vehicletrackingapp.data.model.TripEntry).\n"
                  + " Expected:\n" + _infoTrips + "\n"
                  + " Found:\n" + _existingTrips);
        }
        final HashMap<String, TableInfo.Column> _columnsMaintenance = new HashMap<String, TableInfo.Column>(14);
        _columnsMaintenance.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("vehicleId", new TableInfo.Column("vehicleId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("driverId", new TableInfo.Column("driverId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("maintenanceType", new TableInfo.Column("maintenanceType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("time", new TableInfo.Column("time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("cost", new TableInfo.Column("cost", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("serviceNotes", new TableInfo.Column("serviceNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("billImageUri", new TableInfo.Column("billImageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("oilChangeDone", new TableInfo.Column("oilChangeDone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("tyreStatusOk", new TableInfo.Column("tyreStatusOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenance.put("batteryStatusOk", new TableInfo.Column("batteryStatusOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaintenance = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMaintenance = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMaintenance = new TableInfo("maintenance", _columnsMaintenance, _foreignKeysMaintenance, _indicesMaintenance);
        final TableInfo _existingMaintenance = TableInfo.read(db, "maintenance");
        if (!_infoMaintenance.equals(_existingMaintenance)) {
          return new RoomOpenHelper.ValidationResult(false, "maintenance(com.vehicletrackingapp.data.model.MaintenanceRecord).\n"
                  + " Expected:\n" + _infoMaintenance + "\n"
                  + " Found:\n" + _existingMaintenance);
        }
        final HashMap<String, TableInfo.Column> _columnsVehicles = new HashMap<String, TableInfo.Column>(11);
        _columnsVehicles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("number", new TableInfo.Column("number", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("model", new TableInfo.Column("model", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("imageUri", new TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("assignedDriverId", new TableInfo.Column("assignedDriverId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("registrationNumber", new TableInfo.Column("registrationNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("fuelType", new TableInfo.Column("fuelType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("mileage", new TableInfo.Column("mileage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicles.put("insuranceStatus", new TableInfo.Column("insuranceStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVehicles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVehicles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVehicles = new TableInfo("vehicles", _columnsVehicles, _foreignKeysVehicles, _indicesVehicles);
        final TableInfo _existingVehicles = TableInfo.read(db, "vehicles");
        if (!_infoVehicles.equals(_existingVehicles)) {
          return new RoomOpenHelper.ValidationResult(false, "vehicles(com.vehicletrackingapp.data.model.Vehicle).\n"
                  + " Expected:\n" + _infoVehicles + "\n"
                  + " Found:\n" + _existingVehicles);
        }
        final HashMap<String, TableInfo.Column> _columnsDrivers = new HashMap<String, TableInfo.Column>(7);
        _columnsDrivers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("licenseNumber", new TableInfo.Column("licenseNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrivers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDrivers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDrivers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDrivers = new TableInfo("drivers", _columnsDrivers, _foreignKeysDrivers, _indicesDrivers);
        final TableInfo _existingDrivers = TableInfo.read(db, "drivers");
        if (!_infoDrivers.equals(_existingDrivers)) {
          return new RoomOpenHelper.ValidationResult(false, "drivers(com.vehicletrackingapp.data.model.Driver).\n"
                  + " Expected:\n" + _infoDrivers + "\n"
                  + " Found:\n" + _existingDrivers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d663f9789d9a75af37711f430f07f219", "05235231ce90d7daf88f7e2b0e1b6052");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "trips","maintenance","vehicles","drivers");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `trips`");
      _db.execSQL("DELETE FROM `maintenance`");
      _db.execSQL("DELETE FROM `vehicles`");
      _db.execSQL("DELETE FROM `drivers`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AppDao.class, AppDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AppDao dao() {
    if (_appDao != null) {
      return _appDao;
    } else {
      synchronized(this) {
        if(_appDao == null) {
          _appDao = new AppDao_Impl(this);
        }
        return _appDao;
      }
    }
  }
}
