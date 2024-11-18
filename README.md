# Orchard Management System

## 1. Crop Monitoring (draft)
- **Main Page:** Finished
- **Crop Detail Page:** Finished
- **Demand Information Page:** Finished
- **History Page:** Finished

## 固定代码
- **顶部栏+back按钮xml代码（如果用linear layout）**

        <LinearLayout
            android:id="@+id/topBar"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            android:orientation="horizontal"
            android:background="#ADD8E6"
            android:gravity="center_vertical"
            app:layout_constraintTop_toTopOf="parent">

            <ImageButton
                android:id="@+id/backButton"
                android:layout_width="35dp"
                android:layout_height="38dp"
                android:layout_marginStart="8dp"
                android:background="?android:attr/selectableItemBackground"
                android:contentDescription="Back"
                android:scaleType="fitCenter"
                android:src="@drawable/icon_back" />
        </LinearLayout>


    

- **底部导航栏代码**

      <LinearLayout
        android:id="@+id/bottomNavBar"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:gravity="center"
        android:background="#ADD8E6"
        android:padding="10dp"
        app:layout_constraintBottom_toBottomOf="parent">

        <ImageButton
            android:id="@+id/crop"
            android:layout_width="0dp"
            android:layout_height="60dp"
            android:layout_weight="1"
            android:background="?android:attr/selectableItemBackground"
            android:contentDescription="Crop"
            android:padding="10dp"
            android:src="@drawable/icon_monitor"
            android:scaleType="fitCenter" />

        <ImageButton
            android:id="@+id/warehouse"
            android:layout_width="0dp"
            android:layout_height="60dp"
            android:layout_weight="1"
            android:background="?android:attr/selectableItemBackground"
            android:contentDescription="Inventory"
            android:padding="10dp"
            android:src="@drawable/icon_warehouse"
            android:scaleType="fitCenter"/>

        <ImageButton
            android:id="@+id/calendar"
            android:layout_width="0dp"
            android:layout_height="60dp"
            android:layout_weight="1"
            android:background="?android:attr/selectableItemBackground"
            android:contentDescription="task"
            android:padding="10dp"
            android:src="@drawable/icon_calendar"
            android:scaleType="fitCenter"/>

        <ImageButton
            android:id="@+id/map"
            android:layout_width="0dp"
            android:layout_height="60dp"
            android:layout_weight="1"
            android:background="?android:attr/selectableItemBackground"
            android:contentDescription="map"
            android:padding="10dp"
            android:src="@drawable/icon_map"
            android:scaleType="fitCenter"/>
        </LinearLayout>

