import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTooltipModule} from '@angular/material/tooltip';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MdcTopAppBarModule} from '@angular-mdc/web';
import {NgxMatSelectSearchModule} from 'ngx-mat-select-search';
import {MatSnackBarModule} from '@angular/material/snack-bar';

@NgModule({
  imports: [MatSortModule, MatTabsModule, MdcTopAppBarModule, MatListModule, MatSidenavModule, MatButtonModule,
    MatCheckboxModule, MatTooltipModule, MatInputModule, MatDatepickerModule, MatSelectModule, MatPaginatorModule,
    MatTableModule, MatDialogModule, MatNativeDateModule, MatIconModule, DragDropModule, NgxMatSelectSearchModule,
    MatExpansionModule, MatMenuModule, MatSnackBarModule],
  exports: [MatSortModule, MatTabsModule, MdcTopAppBarModule, MatListModule, MatSidenavModule, MatButtonModule,
    MatCheckboxModule, MatTooltipModule, MatInputModule, MatDatepickerModule, MatSelectModule, MatPaginatorModule,
    MatTableModule, MatDialogModule, MatNativeDateModule, MatIconModule, DragDropModule, NgxMatSelectSearchModule,
    MatExpansionModule, MatMenuModule, MatSnackBarModule]
})
export class AppMaterialModule {
}
