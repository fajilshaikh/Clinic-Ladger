package com.clinicledger.app.data.database;

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
import com.clinicledger.app.data.dao.ClinicDao;
import com.clinicledger.app.data.dao.ClinicDao_Impl;
import com.clinicledger.app.data.dao.PatientVisitDao;
import com.clinicledger.app.data.dao.PatientVisitDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ClinicDao _clinicDao;

  private volatile PatientVisitDao _patientVisitDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `clinics` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `isDefault` INTEGER NOT NULL, `logoPath` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `patient_visits` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `patientName` TEXT NOT NULL, `amount` INTEGER NOT NULL, `clinicId` INTEGER NOT NULL, `visitDate` INTEGER NOT NULL, `notes` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`clinicId`) REFERENCES `clinics`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_patient_visits_clinicId` ON `patient_visits` (`clinicId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_patient_visits_visitDate` ON `patient_visits` (`visitDate`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_patient_visits_patientName` ON `patient_visits` (`patientName`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '85de45e0a18adff4baeb832eb5617916')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `clinics`");
        db.execSQL("DROP TABLE IF EXISTS `patient_visits`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsClinics = new HashMap<String, TableInfo.Column>(7);
        _columnsClinics.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("logoPath", new TableInfo.Column("logoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClinics = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClinics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClinics = new TableInfo("clinics", _columnsClinics, _foreignKeysClinics, _indicesClinics);
        final TableInfo _existingClinics = TableInfo.read(db, "clinics");
        if (!_infoClinics.equals(_existingClinics)) {
          return new RoomOpenHelper.ValidationResult(false, "clinics(com.clinicledger.app.data.entity.Clinic).\n"
                  + " Expected:\n" + _infoClinics + "\n"
                  + " Found:\n" + _existingClinics);
        }
        final HashMap<String, TableInfo.Column> _columnsPatientVisits = new HashMap<String, TableInfo.Column>(8);
        _columnsPatientVisits.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("patientName", new TableInfo.Column("patientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("clinicId", new TableInfo.Column("clinicId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("visitDate", new TableInfo.Column("visitDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatientVisits.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatientVisits = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPatientVisits.add(new TableInfo.ForeignKey("clinics", "RESTRICT", "NO ACTION", Arrays.asList("clinicId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPatientVisits = new HashSet<TableInfo.Index>(3);
        _indicesPatientVisits.add(new TableInfo.Index("index_patient_visits_clinicId", false, Arrays.asList("clinicId"), Arrays.asList("ASC")));
        _indicesPatientVisits.add(new TableInfo.Index("index_patient_visits_visitDate", false, Arrays.asList("visitDate"), Arrays.asList("ASC")));
        _indicesPatientVisits.add(new TableInfo.Index("index_patient_visits_patientName", false, Arrays.asList("patientName"), Arrays.asList("ASC")));
        final TableInfo _infoPatientVisits = new TableInfo("patient_visits", _columnsPatientVisits, _foreignKeysPatientVisits, _indicesPatientVisits);
        final TableInfo _existingPatientVisits = TableInfo.read(db, "patient_visits");
        if (!_infoPatientVisits.equals(_existingPatientVisits)) {
          return new RoomOpenHelper.ValidationResult(false, "patient_visits(com.clinicledger.app.data.entity.PatientVisit).\n"
                  + " Expected:\n" + _infoPatientVisits + "\n"
                  + " Found:\n" + _existingPatientVisits);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "85de45e0a18adff4baeb832eb5617916", "2f044775537157d796b590fa2fe9d2fe");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "clinics","patient_visits");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `patient_visits`");
      _db.execSQL("DELETE FROM `clinics`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(ClinicDao.class, ClinicDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PatientVisitDao.class, PatientVisitDao_Impl.getRequiredConverters());
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
  public ClinicDao clinicDao() {
    if (_clinicDao != null) {
      return _clinicDao;
    } else {
      synchronized(this) {
        if(_clinicDao == null) {
          _clinicDao = new ClinicDao_Impl(this);
        }
        return _clinicDao;
      }
    }
  }

  @Override
  public PatientVisitDao patientVisitDao() {
    if (_patientVisitDao != null) {
      return _patientVisitDao;
    } else {
      synchronized(this) {
        if(_patientVisitDao == null) {
          _patientVisitDao = new PatientVisitDao_Impl(this);
        }
        return _patientVisitDao;
      }
    }
  }
}
