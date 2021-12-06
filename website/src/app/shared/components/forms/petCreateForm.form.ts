import {PetType} from '../../../services/backend/enums';
import {CreatePetRequest, CreatePetResponse, PetApiService} from '../../../services/backend/petApi.service';
import {PetTypeDropDown} from '../dropdowns/petTypeDropDown.dropdown';
import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {debounceTime, tap} from 'rxjs/operators';

export class PetCreateFormModel {
  ownerId: number;
  name: string;
  birthdate: Date;
  petType: PetType;
  vaccinated: boolean;

  constructor(ownerId: number,
              name: string,
              birthdate: Date,
              petType: PetType,
              vaccinated: boolean) {
    this.ownerId = ownerId;
    this.name = name;
    this.birthdate = birthdate;
    this.petType = petType;
    this.vaccinated = vaccinated;
  }

}

@Component({
  selector: 'pet-create-form',
  templateUrl: './petCreateForm.form.html',
  styleUrls: ['./petCreateForm.form.scss']
})
export class PetCreateForm implements OnInit, AfterViewInit {
  @Input() model: PetCreateFormModel = new PetCreateFormModel(null, '', null, null, false);
  @ViewChild(PetTypeDropDown)
  private readonly petTypeElement: PetTypeDropDown;
  submitDisabled = false;
  formGroup: FormGroup;
  ownerIdControl: FormControl;
  nameControl: FormControl;
  birthdateControl: FormControl;
  petTypeControl: FormControl;
  vaccinatedControl: FormControl;

  constructor(private readonly petApi: PetApiService, private readonly router: Router, private readonly fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.init();
    this.formGroup = this.fb.group({
      ownerId: new FormControl(this.model.ownerId, [
        Validators.required,
        Validators.max(9223372036854775807)], []),
      name: new FormControl(this.model.name, [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(40)], []),
      birthdate: new FormControl(this.model.birthdate, [
        Validators.required], []),
      petType: new FormControl(this.model.petType, [
        Validators.required], []),
      vaccinated: new FormControl(this.model.vaccinated, [
        Validators.required], [])
    });
    this.ownerIdControl = this.formGroup.get('ownerId') as FormControl;
    this.nameControl = this.formGroup.get('name') as FormControl;
    this.birthdateControl = this.formGroup.get('birthdate') as FormControl;
    this.petTypeControl = this.formGroup.get('petType') as FormControl;
    this.vaccinatedControl = this.formGroup.get('vaccinated') as FormControl;

    this.formGroup.statusChanges.pipe(tap(() => {
      // fill the model with new data
      this.model.ownerId = this.ownerIdControl.value;
      this.model.name = this.nameControl.value;
      this.model.birthdate = this.birthdateControl.value;
      this.model.petType = this.petTypeControl.value;
      this.model.vaccinated = this.vaccinatedControl.value;
    }), debounceTime(675)).subscribe((newStatus) => {

    });

  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  submit() {
    this.submitDisabled = true;
    this.petApi.createPet(new CreatePetRequest(this.model.ownerId,
      this.model.name,
      this.model.birthdate,
      this.model.petType,
      this.model.vaccinated))
      .subscribe((response: CreatePetResponse) => {
        this.submitDisabled = false;
        this.router.navigate(['/private/pets']);
      }, (_) => {
        this.submitDisabled = false;
      });
  }

  onSubmitClick() {
    this.submit();
  }

}

