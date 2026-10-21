package com.clinicledger.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.clinicledger.app.data.entity.PatientVisit;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class PatientVisitDao_Impl implements PatientVisitDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PatientVisit> __insertionAdapterOfPatientVisit;

  private final EntityDeletionOrUpdateAdapter<PatientVisit> __deletionAdapterOfPatientVisit;

  private final EntityDeletionOrUpdateAdapter<PatientVisit> __updateAdapterOfPatientVisit;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVisitById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVisitsByClinicId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllVisits;

  public PatientVisitDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatientVisit = new EntityInsertionAdapter<PatientVisit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `patient_visits` (`id`,`patientName`,`amount`,`clinicId`,`visitDate`,`notes`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PatientVisit entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPatientName());
        statement.bindLong(3, entity.getAmount());
        statement.bindLong(4, entity.getClinicId());
        statement.bindLong(5, entity.getVisitDate());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfPatientVisit = new EntityDeletionOrUpdateAdapter<PatientVisit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `patient_visits` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PatientVisit entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPatientVisit = new EntityDeletionOrUpdateAdapter<PatientVisit>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `patient_visits` SET `id` = ?,`patientName` = ?,`amount` = ?,`clinicId` = ?,`visitDate` = ?,`notes` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PatientVisit entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPatientName());
        statement.bindLong(3, entity.getAmount());
        statement.bindLong(4, entity.getClinicId());
        statement.bindLong(5, entity.getVisitDate());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteVisitById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM patient_visits WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteVisitsByClinicId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM patient_visits WHERE clinicId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllVisits = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM patient_visits";
        return _query;
      }
    };
  }

  @Override
  public Object insertVisit(final PatientVisit visit,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPatientVisit.insertAndReturnId(visit);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<PatientVisit> visits,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPatientVisit.insert(visits);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVisit(final PatientVisit visit,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPatientVisit.handle(visit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateVisit(final PatientVisit visit,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPatientVisit.handle(visit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVisitById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVisitById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteVisitById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVisitsByClinicId(final long clinicId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVisitsByClinicId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, clinicId);
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
          __preparedStmtOfDeleteVisitsByClinicId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllVisits(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllVisits.acquire();
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
          __preparedStmtOfDeleteAllVisits.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getVisitById(final long id, final Continuation<? super PatientVisit> $completion) {
    final String _sql = "SELECT * FROM patient_visits WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PatientVisit>() {
      @Override
      @Nullable
      public PatientVisit call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final PatientVisit _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Flow<List<PatientVisit>> getTodayVisitsByClinic(final long clinicId, final long startOfDay,
      final long endOfDay) {
    final String _sql = "SELECT * FROM patient_visits WHERE clinicId = ? AND visitDate >= ? AND visitDate <= ? ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startOfDay);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endOfDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<PatientVisit>> getTodayVisitsAllClinics(final long startOfDay,
      final long endOfDay) {
    final String _sql = "SELECT * FROM patient_visits WHERE visitDate >= ? AND visitDate <= ? ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startOfDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endOfDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getTodayPatientCountByClinic(final long clinicId, final long startOfDay,
      final long endOfDay) {
    final String _sql = "SELECT COUNT(*) FROM patient_visits WHERE clinicId = ? AND visitDate >= ? AND visitDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startOfDay);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endOfDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Flow<Long> getTodayCollectionByClinic(final long clinicId, final long startOfDay,
      final long endOfDay) {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM patient_visits WHERE clinicId = ? AND visitDate >= ? AND visitDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startOfDay);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endOfDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final long _tmp;
            _tmp = _cursor.getLong(0);
            _result = _tmp;
          } else {
            _result = 0L;
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
  public Flow<List<PatientVisit>> getVisitsByDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM patient_visits WHERE visitDate >= ? AND visitDate <= ? ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<PatientVisit>> getVisitsByClinicAndDateRange(final long clinicId,
      final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM patient_visits WHERE clinicId = ? AND visitDate >= ? AND visitDate <= ? ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<PatientVisit>> searchPatients(final String query) {
    final String _sql = "SELECT * FROM patient_visits WHERE patientName LIKE '%' || ? || '%' ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<PatientVisit>> searchPatientsInClinic(final long clinicId, final String query) {
    final String _sql = "SELECT * FROM patient_visits WHERE clinicId = ? AND patientName LIKE '%' || ? || '%' ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getVisitCountForClinic(final long clinicId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM patient_visits WHERE clinicId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, clinicId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecentNotes(final Continuation<? super List<String>> $completion) {
    final String _sql = "\n"
            + "        SELECT notes FROM patient_visits\n"
            + "        WHERE notes IS NOT NULL AND TRIM(notes) != ''\n"
            + "        GROUP BY notes\n"
            + "        ORDER BY MAX(updatedAt) DESC\n"
            + "        LIMIT 30\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ClinicAmountSummary>> getClinicBreakdown(final long start, final long end) {
    final String _sql = "\n"
            + "        SELECT clinicId, COUNT(*) as totalPatients, COALESCE(SUM(amount), 0) as totalAmount\n"
            + "        FROM patient_visits\n"
            + "        WHERE visitDate >= ? AND visitDate <= ?\n"
            + "        GROUP BY clinicId\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<ClinicAmountSummary>>() {
      @Override
      @NonNull
      public List<ClinicAmountSummary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClinicId = 0;
          final int _cursorIndexOfTotalPatients = 1;
          final int _cursorIndexOfTotalAmount = 2;
          final List<ClinicAmountSummary> _result = new ArrayList<ClinicAmountSummary>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClinicAmountSummary _item;
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final int _tmpTotalPatients;
            _tmpTotalPatients = _cursor.getInt(_cursorIndexOfTotalPatients);
            final long _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getLong(_cursorIndexOfTotalAmount);
            _item = new ClinicAmountSummary(_tmpClinicId,_tmpTotalPatients,_tmpTotalAmount);
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
  public Flow<List<PatientVisit>> getAllVisits() {
    final String _sql = "SELECT * FROM patient_visits ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patient_visits"}, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getAllVisitsList(final Continuation<? super List<PatientVisit>> $completion) {
    final String _sql = "SELECT * FROM patient_visits ORDER BY visitDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PatientVisit>>() {
      @Override
      @NonNull
      public List<PatientVisit> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPatientName = CursorUtil.getColumnIndexOrThrow(_cursor, "patientName");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfVisitDate = CursorUtil.getColumnIndexOrThrow(_cursor, "visitDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PatientVisit> _result = new ArrayList<PatientVisit>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatientVisit _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPatientName;
            _tmpPatientName = _cursor.getString(_cursorIndexOfPatientName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final long _tmpClinicId;
            _tmpClinicId = _cursor.getLong(_cursorIndexOfClinicId);
            final long _tmpVisitDate;
            _tmpVisitDate = _cursor.getLong(_cursorIndexOfVisitDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PatientVisit(_tmpId,_tmpPatientName,_tmpAmount,_tmpClinicId,_tmpVisitDate,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
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
