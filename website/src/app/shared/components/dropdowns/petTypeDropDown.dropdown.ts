import {PetType} from '../../../services/backend/enums';
import {FocusMonitor} from '@angular/cdk/a11y';
import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {
  AfterViewInit,
  Component,
  DoCheck,
  ElementRef,
  EventEmitter,
  HostBinding,
  Injector,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, FormControl, NgControl} from '@angular/forms';
import {MatFormFieldControl} from '@angular/material/form-field';
import {MatSelect} from '@angular/material/select';
import {Subject} from 'rxjs';

export class Selected {
  item: PetType;

  constructor(item: PetType) {
    this.item = item;
  }

}

@Component({
  selector: 'pet-type-drop-down',
  templateUrl: './petTypeDropDown.dropdown.html',
  styleUrls: ['./petTypeDropDown.dropdown.scss'],
  providers: [
    {
      provide: MatFormFieldControl,
      useExisting: PetTypeDropDown
    }
  ]
})
export class PetTypeDropDown implements OnInit, AfterViewInit, ControlValueAccessor, DoCheck, MatFormFieldControl<PetType>, OnDestroy {
  @Input() selected: PetType = null;
  model: Array<PetType> = [];
  @Output() onSelected = new EventEmitter<Selected>();
  @ViewChild(MatSelect) selectBox: ElementRef;
  isDisabled: boolean;
  @Output() onTouched = new EventEmitter<boolean>();
  ngControl: any;
  needsFiltering: boolean;
  public filterCtrl: FormControl = new FormControl();
  public filtered: Array<PetType>;
  translationMap: { [s: string]: string } = {
    'DOG': 'Dog',
    'CAT': 'Cat',
    'BIRD': 'Bird',
    'FISH': 'Fish',
    'OTHER': 'Other'
  };

  constructor(private readonly injector: Injector, private readonly fm: FocusMonitor, private readonly elRef: ElementRef) {

    // manually setup a value accessor, to avoid circular deps
    this.ngControl = this.injector.get(NgControl);
    if (this.ngControl !== null) {
      this.ngControl.valueAccessor = this;
    }

    fm.monitor(elRef.nativeElement, true).subscribe(origin => {
      this.focused = !!origin;
      this.stateChanges.next();
    });
  }

  ngOnInit(): void {
    this.init();
    // we're not getting the values from the network, it's enum
    this.model = Object.keys(PetType).map((key) => {
      return PetType[key];
    });

      this.setUpFilter();
  }

  ngAfterViewInit(): void {

  }

  ngOnDestroy(): void {
    this.stateChanges.complete();
    this.fm.stopMonitoring(this.elRef.nativeElement);
  }

  init() {

  }

  select(item: PetType) {
    this.selected = item;
    this.onSelected.emit(new Selected(item));
  }


  setUpFilter() {
    this.filtered = this.model.slice();

    if (this.model.length > 5) {
      this.needsFiltering = true;
      this.filterCtrl.valueChanges.subscribe(() => {
        this.applyFilter();
      });
    }
  }

  applyFilter() {
    let search = this.filterCtrl.value;

    if (!search) {
      this.filtered = this.model.slice();
    } else {
      search = search.toLowerCase();

      this.filtered = this.model.filter(
        (field) => field.toLowerCase().includes(search));
    }
  }

  // *********
  // methods and properties used to interface with angular forms
  // *********

  writeValue(item: PetType): void {
    if (item) {
      this.selected = item;
    }
  }

  registerOnChange(fn: any): void {
    this.onSelected.subscribe((selected: Selected) => {
      fn(selected.item);
    });
  }

  registerOnTouched(fn: any): void {
    this.onTouched.subscribe((status) => {
      fn(status);
    });
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }


  // *********
  // methods and properties used to interface with material CDK
  // *********

  set value(item: PetType) {
    this.select(item);
    this.stateChanges.next();
  }

  get value() {
    return this.selected;
  }

  stateChanges = new Subject<void>();

  static nextId = 0;
  @HostBinding() id = `pet-type-drop-down-${PetTypeDropDown.nextId++}`;


  _placeholder: string;
  @Input()
  get placeholder() {
    return this._placeholder;
  }

  set placeholder(plh) {
    this._placeholder = plh;
    this.stateChanges.next();
  }

  focused = false;

  get empty() {
    return this.selected === null;
  }

  @HostBinding('class.floating')
  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }

  _required = false;
  @Input()
  get required() {
    return this._required;
  }

  set required(req) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }


  private _disabled = false;
  @Input()
  get disabled() {
    return this._disabled;
  }

  set disabled(dis) {
    this._disabled = coerceBooleanProperty(dis);
    this.stateChanges.next();
  }

  controlType = 'PetTypeDropDown';
  errorState = false;

  @HostBinding('attr.aria-describedby') describedBy = '';

  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }


  onContainerClick(event: MouseEvent) {
    // no need to click on the container, only the contents are clicked on
  }

  ngDoCheck(): void {
    if (this.ngControl) {
      this.errorState = this.ngControl.invalid && this.ngControl.touched;
      this.stateChanges.next();
    }
  }

  touch() {
    this.onTouched.emit(true);
  }

}

