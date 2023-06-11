$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editStudentForm').validate({
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
        required: '<span class="error-message">Please enter the student\'s name.</span>',
        maxlength: '<span class="error-message">Please enter the student\'s name (name <= 50 characters).</span>'
      },
      age: {
        required: '<span class="error-message">Please enter a valid age for students (age >= 18).</span>',
        number: '<span class="error-message">Please enter a valid age for students (age >= 18).</span>',
        min: '<span class="error-message">Please enter a valid age for students (age >= 18).</span>'
      },
      courseName: {
        required: '<span class="error-message">Please select a course for the student.</span>'
      }
    },
    errorPlacement: function(error, element) {
      error.appendTo(element.parent()); // Gắn thông báo lỗi vào phần tử cha của trường nhập liệu
    },
    submitHandler: function(form) {
      event.preventDefault();

      // Lấy thông tin học viên từ form
      var studentId = $(form).attr('action').split('/').pop();
      var studentName = $('#name').val();
      var studentAge = $('#age').val();
      var studentCourse = $('#course').val();

      // Tạo đối tượng học viên mới
      var updatedStudent = {
        name: studentName,
        age: studentAge,
        courseName: studentCourse
      };

      // Gửi yêu cầu PUT để cập nhật học viên
      $.ajax({
        url: 'http://localhost:8080/api/v1/students/' + studentId,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(updatedStudent)
      })
        .then(function() {
          alert('Student updated successfully!');
          window.location.href = '/students'; // Chuyển hướng về trang danh sách học viên
        })
        .catch(function(xhr) {
          if (xhr.status === 200) {
            alert('Student updated successfully!');
            window.location.href = '/students';
          }
        });
    }
  });
});
