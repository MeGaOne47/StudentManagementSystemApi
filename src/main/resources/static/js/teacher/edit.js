$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editTeacherForm').validate({
    rules: {
      name: {
        required: true,
        maxlength: 50
      },
      age: {
        required: true,
        number: true,
        min: 18
      },
      courseName: {
        required: true
      }
    },
    messages: {
      name: {
        required: '<span class="error-message">Please enter the teacher\'s name.</span>',
        maxlength: '<span class="error-message">Please enter the teacher\'s name (name <= 50 characters).</span>'
      },
      age: {
        required: '<span class="error-message">Please enter a valid age for teachers (age >= 18).</span>',
        number: '<span class="error-message">Please enter a valid age for teachers (age >= 18).</span>',
        min: '<span class="error-message">Please enter a valid age for teachers (age >= 18).</span>'
      },
      courseName: {
        required: '<span class="error-message">Please select a course for the teacher.</span>'
      }
    },
    errorPlacement: function(error, element) {
      error.appendTo(element.parent()); // Gắn thông báo lỗi vào phần tử cha của trường nhập liệu
    },
    submitHandler: function(form) {
      event.preventDefault();

      // Lấy thông tin giáo viên từ form
      var teacherId = $(form).attr('action').split('/').pop();
      var teacherName = $('#name').val();
      var teacherAge = $('#age').val();
      var teacherCourse = $('#course').val();

      // Tạo đối tượng giáo viên mới
      var updatedTeacher = {
        name: teacherName,
        age: teacherAge,
        courseName: teacherCourse
      };

      // Gửi yêu cầu PUT để cập nhật giáo viên
      $.ajax({
        url: 'http://localhost:8080/api/v1/teachers/' + teacherId,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(updatedTeacher)
      })
        .then(function() {
          alert('Teacher updated successfully!');
          window.location.href = '/teachers'; // Chuyển hướng về trang danh sách giáo viên
        })
        .catch(function(xhr) {
          if (xhr.status === 200) {
            alert('Teacher updated successfully!');
            window.location.href = '/teachers';
          }
        });
    }
  });
});
